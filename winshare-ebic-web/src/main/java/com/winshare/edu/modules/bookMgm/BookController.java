package com.winshare.edu.modules.bookMgm;

import com.winshare.edu.common.Constant;
import com.winshare.edu.common.page.PageRequest;
import com.winshare.edu.common.page.WebPage;
import com.winshare.edu.common.web.BaseController;
import com.winshare.edu.modules.system.entity.BookInfo;
import com.winshare.edu.modules.system.entity.BooksCatalogInfo;
import com.winshare.edu.modules.system.entity.SysDic;
import com.winshare.edu.modules.system.mapper.SysDicMapper;
import com.winshare.edu.modules.system.service.BookService;
import com.winshare.edu.modules.system.service.BooksCatalogService;
import com.winshare.edu.modules.user.entity.StudentInfo;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.UUID;

@Controller
@RequestMapping("/book")
public class BookController extends BaseController{

	private static Logger log = Logger.getLogger(BookController.class);

	@Autowired
	private BookService bookService;

	@Autowired
	private SysDicMapper sysDicMapper;

	@Autowired
	private BooksCatalogService booksCatalogService;

	@ModelAttribute("bookInfo")
	public BookInfo get(@RequestParam(required=false) Long id) {
		if (id != null){
			BookInfo b = new BookInfo();
					b.setId(id);
			return bookService.findById(b);
		}else{
			return new BookInfo();
		}
	}
	
	@RequestMapping("/index")
	public String index() {
		return "modules/book/index";
	}
	
	@RequestMapping("/list")
	public String list(BookInfo tv, HttpServletRequest request, Model model) {
		WebPage<BookInfo> page = new WebPage<BookInfo>(bookService.findList(new PageRequest(request), tv));
		model.addAttribute("page", page);
		return "modules/book/list";
	}

	@RequestMapping(value = "/saveBook")
	public String save(BookInfo bookInfo, MultipartRequest multiPartRquest,HttpSession session) {
		int result = 0;
		String [] subjectArr = bookInfo.getSubjectName().split(",");
		String [] publisherArr = bookInfo.getPublisherName().split(",");
		String [] gradeArr = bookInfo.getGradeName().split(",");

		bookInfo.setSubjectCode(subjectArr[0]);
		bookInfo.setSubjectName(subjectArr[1]);

		bookInfo.setPublisherCode(publisherArr[0]);
		bookInfo.setPublisherName(publisherArr[1]);

		bookInfo.setGradeCode(gradeArr[0]);
		bookInfo.setGradeName(gradeArr[1]);

		bookInfo.setsOperatorAccount(Long.parseLong("0"));
		bookInfo.setsOperatorName("001");
		bookInfo.setFileId(Long.parseLong("0"));

 		try{
			String filePath = uploadImg(multiPartRquest,session);
			if(org.apache.commons.lang3.StringUtils.isNoneBlank(filePath)){
				bookInfo.setFilePath(filePath);
			}
			result = bookService.saveOrUpdateStudentInfo(bookInfo);
		}catch(Exception e){
			log.error("BookController save is error :",e);
		}
		return "redirect:/book/list";
	}

	@RequestMapping("/addBook")
	public String add(Model model) {
		SysDic sysDics = new SysDic();
		sysDics.setDicCode("subject");
		List<SysDic> sysDic= sysDicMapper.findLists(sysDics);

		sysDics.setDicCode("press");
		List<SysDic> sysDic2= sysDicMapper.findLists(sysDics);

		sysDics.setDicCode("grade");
		List<SysDic> sysDic3= sysDicMapper.findLists(sysDics);

		model.addAttribute("sysDicXk",sysDic);
		model.addAttribute("sysDicCbs",sysDic2);
		model.addAttribute("sysDicNj",sysDic3);
		return "modules/book/addForm";
	}

	@RequestMapping("/formBook")
	public String form( BookInfo bookInfo, Model model){
		if(null != bookInfo.getId()){
			bookInfo = bookService.findById(bookInfo);
			model.addAttribute("bookInfo",bookInfo);
		}
		SysDic sysDics = new SysDic();

		sysDics.setDicCode("subject");
		List<SysDic> sysDic= sysDicMapper.findLists(sysDics);

		sysDics.setDicCode("press");
		List<SysDic> sysDic2= sysDicMapper.findLists(sysDics);

		//获取章节目录
		List<BooksCatalogInfo> listUite = booksCatalogService.findByBookId(Integer.valueOf(bookInfo.getId().toString()));
		List<BooksCatalogInfo> listKnowledge = booksCatalogService.findKnowledge(Integer.valueOf(bookInfo.getId().toString()));

		model.addAttribute("sysDicXk",sysDic);
		model.addAttribute("sysDicCbs",sysDic2);
		model.addAttribute("bookInfo",bookInfo);

		model.addAttribute("listUite",listUite);
		model.addAttribute("listKnowledge",listKnowledge);

		model.addAttribute("ebicSystemUrl", Constant.myResources.getString("ebicSystemUrl"));
		return "modules/book/formBook";
	}


	private String uploadImg(MultipartRequest multiPartRquest, HttpSession session) throws Exception{
		String relativePath = "";
		InputStream picStream = null;
		try{
			MultipartFile pic = multiPartRquest.getFile("userPic");
			if(pic == null || pic.getSize() <= 0){
				return relativePath;
			}
			picStream = pic.getInputStream();
			String strs[] = pic.getOriginalFilename().split("\\.");
			String suffixType = strs[strs.length - 1];
			String picName = UUID.randomUUID().toString().replaceAll("-", "")+"."+suffixType;
			File basePath = new File(this.getClass().getResource("/").getFile());
			String path = session.getServletContext().getRealPath("/static/images");
			String storePath = basePath.getParentFile().getParentFile().getPath() + File.separator + Constant.myResources.getString("uploadPicPath");
			File dir = new File(path);
			if(!dir.exists()){
				dir.mkdirs();
			}
			relativePath = "../static/images"+File.separator+ picName;
			/*String s = ResourceBundle.getBundle("winshare", Locale.getDefault()).getString("uploadPicPath");
			relativePath =  Constant.myResources.getString("uploadPicPath") +File.separator+ picName;*/
			File picFile = new File(dir.getPath() +File.separator+ picName);
			FileUtils.copyInputStreamToFile(picStream, picFile);
		}finally{
			if(picStream != null){
				IOUtils.closeQuietly(picStream);
			}
		}
		return relativePath;
	}
}
