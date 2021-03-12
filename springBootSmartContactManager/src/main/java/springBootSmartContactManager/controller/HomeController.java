package springBootSmartContactManager.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springBootSmartContactManager.dao.UserRepository;
import springBootSmartContactManager.entity.User;
import springBootSmartContactManager.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		User user=new User();
		user.setName("aelis");
		user.setEmail("aspatel@gmail.com");
		userRepository.save(user);
		return "working";
	}
	@RequestMapping("/")
	public String home(Model m) {
	m.addAttribute("title","home-smart contact manager");
		return "home";
	}
	@RequestMapping("/about")
	public String about(Model m) {
	m.addAttribute("title","home-smart contact manager");
		return "about";
	}
	
	@RequestMapping("/signup")
	public String signup(Model m) {
	m.addAttribute("title","register-smart contact manager");
	m.addAttribute("user", new User());
		return "signup";
	}
	
	//handler for registering user
	@RequestMapping(value="/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user,BindingResult br,@RequestParam(value = "agreement",defaultValue = "false")boolean agreement,Model model,HttpSession session)
	{
		try {
			if(!agreement) {
				System.out.print("you have not agrred the terms and conditions");
				throw new Exception("you have not agrred the terms and conditions");
			}
			if(br.hasErrors())
			{
				System.out.print("error:"+br.toString());
				model.addAttribute("user",user);
				return "signup";
			}
		
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.print(agreement);
			System.out.print(user);
			User result = this.userRepository.save(user);
			model.addAttribute("User",new User());
			session.setAttribute("message", new Message("Successfully Registered!!","alert-success"));
			return "signup";
		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("User",user);
			session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(),"alert-danger"));
			return "signup";
		}
		
	}
	
	//handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		model.addAttribute("title", "login page");
		return "login";
	}
	
	
}
