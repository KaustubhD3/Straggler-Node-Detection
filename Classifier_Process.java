package com.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ML.*;
import com.connection.Dbconn;

/**
 * Servlet implementation class Classifier_Process
 */
@WebServlet("/Classifier_Process")
public class Classifier_Process extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Classifier_Process() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw=response.getWriter(); 
		try {
	            int _mType = 0;
	            // System.out.println(jList1.getSelectedValuesList());
	         
	            String train = Dbconn.trainarff_file;
	            String test = Dbconn.testarff_file;

	           System.out.println("Selected Classifier is Modified J48");
	                ClassifierDT cs = new ClassifierDT();
	                cs.Execute(train, test);
	                System.out.println("Selected Classifier is Multi Class Classifier");
	                ClassifierMLC mlc = new ClassifierMLC();
	                mlc.Execute(train, test);
                 System.out.println("Selected Classifier is RF");
	                ClassifierRF rf = new ClassifierRF();
	                rf.Execute(train, test);
	                pw.println("<script> alert('Classifier Done');</script>");
	                RequestDispatcher rd = request.getRequestDispatcher("/AdminHome.jsp");
					rd.include(request, response); 
	        } catch (Exception ex) {
	            
	        }
	}

}
