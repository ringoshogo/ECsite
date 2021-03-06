/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jums;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author guest1Day
 */
public class UpdateConfirm extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        String ac = request.getParameter("ac");
        if(ac == null || !ac.equals( String.valueOf( session.getAttribute("ac") )) ){
            request.setAttribute("error", "不正アクセスです");
            request.getRequestDispatcher("/error.jsp").forward(request,response);
        }else{
            try  {
                String name = (String)request.getParameter("name").trim();
                String pass = (String)request.getParameter("pass").trim();
                String address = (String)request.getParameter("address").trim();
                String mail = (String)request.getParameter("mail").trim();
                String check = "";
                int userID = Integer.parseInt( request.getParameter("userID") );

                UserDataBeans udb = new UserDataDAO().searchByUserID(userID);
                udb.setName(name);
                udb.setPass(pass);
                udb.setAddress(address);
                udb.setMail(mail);
                check = udb.check();

                session.setAttribute("updateUserData",udb);
                request.setAttribute("check",check);
                request.getRequestDispatcher("/updateconfirm.jsp").forward(request,response);
            }catch(Exception e){
                request.setAttribute("error", e.getMessage());
                request.getRequestDispatcher("/error.jsp").forward(request,response);
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
