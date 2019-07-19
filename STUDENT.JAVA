package com.letstartcoding.springbootexample.controller;



import java.util.ArrayList;

import java.util.List;



import javax.validation.Valid;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;

import org.springframework.ui.ModelMap;

import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.servlet.ModelAndView;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import com.letstartcoding.springbootexample.model.Student;

import com.letstartcoding.springbootexample.service.StudentService;



@Controller



public class StudentEnrollmentController {

	

	 @Autowired

	private StudentService studentService;

	

	@RequestMapping(value ="/enroll",method = RequestMethod.GET)

	public String newRegistration(ModelMap model) {

		Student student = new Student();

		model.addAttribute("student", student);

		return "enroll";

	}

	

	@RequestMapping(value ="/save",method = RequestMethod.POST)

	public String saveRegistration(@Valid Student student,

			BindingResult result, ModelMap model,RedirectAttributes redirectAttributes) {



		if (result.hasErrors()) {

			return "enroll";//will redirect to viewemp request mapping  

		}

		studentService.save(student);		

		//redirectAttributes.addFlashAttribute("message", "Student " + student.getFirstName()+" "+student.getLastName() + " saved");

		return "redirect:/viewstudents/1";//will redirect to viewemp request mapping  

	}

	

	@RequestMapping("/viewstudents")  

    public ModelAndView viewstudents(){  

        List<Student> list=studentService.getAllStudents();

        return new ModelAndView("viewstudents","list",list);  

    } 



	@RequestMapping(value="/viewstudents/{pageid}")  

    public ModelAndView edit(@PathVariable int pageid){  

        int total=2;  

        if(pageid==1){}  

        else{  

            pageid=(pageid-1)*total+1;  

        }  

        List<Student> list=studentService.getStudentsByPage(pageid,total);  

          

        return new ModelAndView("viewstudents","list",list);  

    }


	 @RequestMapping(value="/editstudent/{id}")  

	    public String edit(@PathVariable int id,ModelMap model){  

	       Student student=studentService.getStudentById(id);  

	       model.addAttribute("student", student);

			return "editstudent";

	      }

	 @RequestMapping(value="/editsave",method = RequestMethod.POST)  

	    public ModelAndView editsave(@ModelAttribute("student") Student emp){  

	    	System.out.println("id is"+emp.getId());

	    	studentService.update(emp);  

	        return new ModelAndView("redirect:/viewstudents/1");  

	    }  

	
	    @RequestMapping(value="/deletestudent/{id}",method = RequestMethod.GET)  

	    public ModelAndView delete(@PathVariable int id){  

	    	studentService.delete(id);  

	        return new ModelAndView("redirect:/viewstudents/1");  

	    }  
	    @RequestMapping(value="/delete",method = RequestMethod.GET)  

	    public ModelAndView delete(){  

	    	studentService.delete();  

	        return new ModelAndView("redirect:/enroll");  

	    }  

	

	@ModelAttribute("sections")

	public List<String> initializeSections() {



		List<String> sections = new ArrayList<String>();

		sections.add("Graduate");

		sections.add("Post Graduate");

		sections.add("Research");

		return sections;

	}



	/*

	 * Method used to populate the country list in view. Note that here you can

	 * call external systems to provide real data.

	 */

	@ModelAttribute("countries")

	public List<String> initializeCountries() {



		List<String> countries = new ArrayList<String>();

		countries.add("INDIA");

		countries.add("USA");

		countries.add("CANADA");

		countries.add("FRANCE");

		countries.add("GERMANY");

		countries.add("ITALY");

		countries.add("OTHER");

		return countries;

}

	@ModelAttribute("subjects")

	public List<String> initializeSubjects() {



		List<String> subjects = new ArrayList<String>();

		subjects.add("Physics");

		subjects.add("Chemistry");

		subjects.add("Life Science");

		subjects.add("Political Science");

		subjects.add("Computer Science");

		subjects.add("Mathmatics");

		return subjects;

	}

	 @ModelAttribute("pageCount")

		public List<String> initializePageCount() {

		    int total=2;  

			List<String> pageCount = new ArrayList<String>();

			int count=studentService.count(); 

			int result=((count/total)+ (count%total));

			for(int k=0;k<result;k++) {

				pageCount.add(new Integer(k).toString());

			}

			

			return pageCount;

		}



}
