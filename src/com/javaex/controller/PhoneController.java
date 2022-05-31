package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.PhoneDao;
import com.javaex.vo.PersonVo;

@WebServlet("/pbc")
public class PhoneController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	// get 방식 요청시 호출
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		switch (action) {
			case "writeForm":
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/writeForm.jsp");
				rd.forward(request, response);
				break;
			
			case "list":		
				PhoneDao phoneDao = new PhoneDao();
				List<PersonVo> pList = phoneDao.personSelect();
				
				request.setAttribute("pList", pList);
				
				rd = request.getRequestDispatcher("/WEB-INF/list.jsp");
				rd.forward(request, response);
				break;
				
			case "write":
				String name = request.getParameter("name");
				String hp = request.getParameter("hp");
				String company = request.getParameter("company");
				
				phoneDao = new PhoneDao();
				phoneDao.personInsert(new PersonVo(name, hp, company));
				
				response.sendRedirect("/phonebook2/pbc?action=list");
				break;
				
			case "delete":
				int id = Integer.parseInt(request.getParameter("id"));
				
				phoneDao = new PhoneDao();
				phoneDao.personDelete(id);
				
				response.sendRedirect("/phonebook2/pbc?action=list");
				break;
				
			case "updateForm":
				id = Integer.parseInt(request.getParameter("id"));
				
				phoneDao = new PhoneDao();
				PersonVo p = phoneDao.personSelect(id);
				
				request.setAttribute("id", id);
				request.setAttribute("p", p);
				
				rd = request.getRequestDispatcher("/WEB-INF/updateForm.jsp");
				rd.forward(request, response);
				break;
				
			case "update":
				id = Integer.parseInt(request.getParameter("id"));
				name = request.getParameter("name");
				hp = request.getParameter("hp");
				company = request.getParameter("company");
				
				phoneDao = new PhoneDao();
				phoneDao.personUpdate(id, new PersonVo(name, hp, company));
				
				response.sendRedirect("/phonebook2/pbc?action=list");
				break;				
		}
	}

	// post 방식 요청시 호출
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		doGet(request, response);
	}
}
