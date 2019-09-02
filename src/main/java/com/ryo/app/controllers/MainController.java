package com.ryo.app.controllers;

import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ryo.app.model.MapTable;

@Controller
public class MainController {
	@PersistenceContext
	EntityManager em;

	@RequestMapping("/")
	  public String index() {
		 return "hello.html";
	  }
	  
	  @RequestMapping("/trace")
	  @ResponseBody
	  public String indexTrace() {	
	    return "This is trace Hello World!!!";
	  }
	  
	  @RequestMapping(value = "/home", method = RequestMethod.GET)
	  public ModelAndView indexHome() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("home");
		return modelAndView;
	  }  
	  
	  //http://localhost:8080/AllMethod/homeparam/satu/dua
	  @RequestMapping(value = "/homeparam/{id1}/{id2}", method = RequestMethod.GET)
	  @ResponseBody
	  public String indexHomeParam(@PathVariable String id1, @PathVariable String id2) {
		return "Hasilnya "+id1+" - "+id2;
	  }  
	  
	  //http://localhost:8080/AllMethod/oldparam?id=sa&second=tu
	  @RequestMapping(
			  value = "/oldparam", 
			  params = { "id", "second" }, 
			  method = RequestMethod.GET)
	  @ResponseBody
	  public String indexOldHomeParam(@RequestParam("id") String id1, @RequestParam("second") String id2) {
		return "Hasilnya Old param "+id1+" - "+id2;
	  }  
	  
	  @RequestMapping("/login")
	  public ModelAndView indexLogin(HttpServletRequest request) {
		  HttpSession session = request.getSession();
		  ModelAndView modelAndView = new ModelAndView();
		  if(session.isNew()){
			  modelAndView.setViewName("login");
			}else{		
	 
				modelAndView.addObject("username", session.getAttribute("username").toString());
				modelAndView.setViewName("afterlogin");
			}	
	
		  return modelAndView;
	  }
	  
	  @SuppressWarnings("unchecked")
	  @PostMapping("/postparam")
	  public ModelAndView indexPostParam(@RequestParam("username") String id1, @RequestParam("pwd") String id2, HttpServletRequest request) {
		  List<MapTable> obj = em.createQuery("from MapTable where data1='"+id1+"' and data2='"+id2+"'").getResultList();
		  System.out.println("Length : "+obj.size());
		  System.out.println("Value "+obj);
		  
		/*
		 * Iterator<MapTable> iter = obj.iterator(); iter.hasNext(); String tmpUser =
		 * ((MapTable)iter.next()).getData1();
		 */
		  
		  String tmpUser = ((MapTable)obj.get(0)).getData1();
		  
		  request.getSession().setAttribute("username", tmpUser);	  
		  ModelAndView modelAndView = new ModelAndView();
		  modelAndView.addObject("username", tmpUser);
		  modelAndView.setViewName("afterlogin");
		  return modelAndView;
	  }  
	  
	  @GetMapping("/logout")
	  public String indexGetParam(HttpServletRequest request) {
		  HttpSession session = request.getSession();	  
		  if(session.isNew()){
	
			}else{			
				session.invalidate();
			}	
		  return "login.html";
	  }
	  
}