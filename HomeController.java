package com.csueb.controller;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.csueb.bean.Cart;
import com.csueb.bean.User;
import com.csueb.util.UserService;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	@Autowired
	private UserService userService;	
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "home";
	}
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String result(Model model, HttpServletRequest request, HttpSession session) {
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		if (email.equals("admin@pizza.com") && password.equals("admin")) {
			List<User> users = userService.getUsers();
			session.setAttribute("allUsers", users);
			return "admin";
		}
		User user = userService.validate(email, password);
		if (user == null) {
			session.setAttribute("error", "Invalid Login Credentials");
			return "home";
		} else {
			session.setAttribute("curUser", user);
			session.setAttribute("email", email);
			session.removeAttribute("cart");
			return "success";
		}

	}
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String saveUser(Model model, HttpServletRequest request, HttpSession session) {
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		User user = new User(firstName, lastName, email, password);
		userService.create(user);
		session.setAttribute("curUser", user);
		session.setAttribute("email", email);
		session.removeAttribute("cart");
		return "success";
	}

	@RequestMapping(value = "/cart", method = RequestMethod.POST)
	protected String addtocart(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cart shoppingCart;
		shoppingCart = (Cart) session.getAttribute("cart");
		if (shoppingCart == null) {
			shoppingCart = new Cart();
			session.setAttribute("cart", shoppingCart);
		}
		String name = request.getParameter("name");
		Integer price = Integer.parseInt(request.getParameter("price"));
		shoppingCart.addtocart(name, price);
		model.addAttribute("cart", shoppingCart);
		return "addtocart";
	}

	@RequestMapping(value = "/success", method = RequestMethod.GET)
	public String success(Model model) {
		return "success";
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	protected String deleteItem(Model model, HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Cart shoppingCart;
		shoppingCart = (Cart) session.getAttribute("cart");
		if (shoppingCart == null) {
			shoppingCart = new Cart();
			session.setAttribute("cart", shoppingCart);
		}
		String name = request.getParameter("name");
		shoppingCart.deleteitem(name);
		return "addtocart";
	}

	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public String payment(Model model) {
		return "checkout";
	}

	@RequestMapping(value = "/thanku", method = RequestMethod.POST)
	public String thanq(Model model) {
		return "thanq";
	}

	@RequestMapping(value = "/change", method = RequestMethod.GET)
	public String chg(Model model, HttpSession session) {
		User user = (User) session.getAttribute("curUser");
		System.out.println("User email is " + user.getEmail());
		return "change";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(Model model, HttpSession session, HttpServletRequest request) {
		User user = (User) session.getAttribute("curUser");
		String newpassword = request.getParameter("newpassword");
		user.setPassword(newpassword);
		userService.update(user);
		return "success";
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public String deleteUser(Model model, HttpServletRequest request, HttpSession session) {
		Integer custID = Integer.parseInt(request.getParameter("custid"));
		userService.delete(custID);
		List<User> users = userService.getUsers();
		session.setAttribute("allUsers", users);
		return "admin";
	}
	
	@RequestMapping(value = "/back", method = RequestMethod.GET)
	public String backToHome(Locale locale, Model model) {

		return "home";
	}
}