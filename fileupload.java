package com.admin;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class fileupload
 */
@WebServlet("/fileupload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
maxFileSize = 1024 * 1024 * 10, // 10MB
maxRequestSize = 1024 * 1024 * 50)
public class fileupload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public fileupload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	String getFileName(Part filePart) {

		String partHeader = filePart.getHeader("content-disposition");
		
		for (String cd : filePart.getHeader("content-disposition").split(";")) {
			if (cd.trim().startsWith("filename")) {
				String fileName = cd.substring(cd.indexOf('=') + 1).trim()
						.replace("\"", "");
				return fileName.substring(fileName.lastIndexOf('/') + 1)
						.substring(fileName.lastIndexOf('\\') + 1);
			}
		}
		return null;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Part filePart = request.getPart("txt_search");
		InputStream inputStream = null;
		try {
			String filename=getFileName(filePart);
			inputStream = filePart.getInputStream();
			Qlearnining.Qlearnining_process(inputStream, filename);
			RequestDispatcher rd = request.getRequestDispatcher("/AdminHome.jsp");
			rd.include(request, response); 
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
