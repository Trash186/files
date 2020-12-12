package com.dgut.controller;

import com.dgut.entity.User;
import com.dgut.entity.UserFile;
import com.dgut.service.UserFileService;
import com.dgut.service.UserService;
import com.sun.deploy.net.HttpResponse;
import com.sun.deploy.net.URLEncoder;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Baller_Xiao
 * @Description
 * @create_time 2020-12-10 17:31
 * @return
 * @Version
 */
@Controller
@RequestMapping("/file")
public class FileController
{

    @Autowired
    private UserFileService userFileService;

    /**
     * 返回所有文件列表 json格式
     */
    @GetMapping("/findAllJSON")
    @ResponseBody
    public List<UserFile> findAllJSON(HttpSession session)
    {

        User user = (User) session.getAttribute("user");

        List<UserFile> userFiles= userFileService.findByUserId(user.getId());

        return userFiles;
    }

    /**
     * 删除文件信息
     */
    @GetMapping("/delete")
    public String delete(String id) throws FileNotFoundException
    {
        UserFile userFile = userFileService.findById(id);
        String realPath=ResourceUtils.getURL("classpath:").getPath()+"/static"+userFile.getPath();

        File file = new File(realPath, userFile.getNewFileName());

        if(file.exists())file.delete();//立即删除

        //删除数据库中的记录
        userFileService.delete(id);
        return "redirect:/file/showAll";
    }

    /**
     * 根据openStyle来判断是下载还是在线打开
     * @param openStyle
     * @param id
     * @param response
     * @throws IOException
     */
    @GetMapping("/download")
   public void download(String openStyle,String id, HttpServletResponse response) throws IOException
    {
        //获取打开方式
        openStyle=openStyle==null?"attachment":openStyle;
        UserFile userFile=userFileService.findById(id);
        if("attachment".equals(openStyle))
        {
            //更新下载次数
            userFile.setDownCounts(userFile.getDownCounts()+1);
            userFileService.update(userFile);
        }

        String realPath=ResourceUtils.getURL("classpath:").getPath()+"/static"+userFile.getPath();
        //获取文件输入流
        FileInputStream is = new FileInputStream(new File(realPath, userFile.getNewFileName()));
        //附件下载 先把头设置一下
        response.setHeader("content-disposition",openStyle+";fileName="+ URLEncoder.encode(userFile.
                getOldFileName(),"UTF-8"));
        //获取文件输出流
        ServletOutputStream os = response.getOutputStream();
        //文件拷贝
        IOUtils.copy(is,os);
        IOUtils.closeQuietly(is);
        IOUtils.closeQuietly(os);
    }

    @PostMapping("/upload")//aaa和表单中的name对应上
    public String upload(MultipartFile aaa,HttpSession session) throws IOException
    {
        User user = (User) session.getAttribute("user");
        //获取文件原始名称
        String oldFileName=aaa.getOriginalFilename();

        //获取文件后缀
        String extension = "."+FilenameUtils.getExtension(aaa.getOriginalFilename());

        //生成新的文件名称
    String newFileName=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+ UUID.randomUUID().toString()
            .replace("-","")+extension;

    //文件大小
        Long size = aaa.getSize();

        //文件类型
        String contentType = aaa.getContentType();
        //处理根据日期生成目录
        String realPath= ResourceUtils.getURL("classpath:").getPath()+"/static/files";
        String  dateFormat = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String dateDirPath=realPath+"/"+ dateFormat;
        File dateDir=new File(dateDirPath);
        if(!dateDir.exists())dateDir.mkdirs();

        //上传
        aaa.transferTo(new File(dateDir,newFileName));

        //将文件信息放入数据库中
        UserFile userFile=new UserFile();

        userFile.setOldFileName(oldFileName);
        userFile.setNewFileName(newFileName);
        userFile.setExt(extension);
        userFile.setSize(String.valueOf(size));
        userFile.setType(contentType);
        userFile.setPath("/files/"+dateFormat);
        userFile.setUserId(user.getId());

        userFileService.save(userFile);


        return "redirect:/file/showAll";

    }

    /**
     * 展示所有文件
     */

    @GetMapping("/showAll")
    public String findAll(HttpSession session, Model model)
    {

        User user = (User) session.getAttribute("user");

        List<UserFile> userFiles= userFileService.findByUserId(user.getId());

        model.addAttribute("files",userFiles);
        return "showAll";
    }

//    public static void main(String[] args) throws FileNotFoundException
//    {
//
//        String realPath= ResourceUtils.getURL("classpath:").getPath()+"/static/files";
//        String dateDirPath=realPath+"/"+new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        System.out.println(realPath);
//        System.out.println("---------------------");
//        System.out.println(dateDirPath);
//    }
}
