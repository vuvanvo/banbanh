/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.control;

import main.java.dao.DAO;
import main.java.entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author trinh
 */
@WebServlet(name = "ShowCartControl", urlPatterns = {"/print"})
public class ShowCartControl extends HttpServlet {

    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Cookie arr[] = request.getCookies();
        PrintWriter out = response.getWriter();
        List<Product> list = new ArrayList<>();
        DAO dao = new DAO();
        for (Cookie o : arr) {
            if (o.getName().equals("id")) {
                String txt[] = o.getValue().split("#");
                for (String s : txt) {
                    if(dao.getProduct(s) !=null)
                        list.add(dao.getProduct(s));
                }
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int count = 1;
            for (int j = i+1; j < list.size(); j++) {
                if(list.get(i) != null && list.get(j) != null && list.get(i).getId() == list.get(j).getId()){
                    count++;
                    list.remove(j);
                    j--;
                    list.get(i).setAmount(count);
                }
            }
        }
        double total = 0;
        int amout = 0;
        for (Product o : list) {
            if(o != null) {
                total = total + o.getAmount() * o.getPrice();
                amout = amout + o.getAmount();
            }
        }
        HttpSession session = request.getSession();
        session.setAttribute("a", amout);

        request.setAttribute("list", list);
        request.setAttribute("amout", amout);
        request.setAttribute("total", total);

        request.setAttribute("vat", 0.1 * total);
        request.setAttribute("sum", total);




        request.getRequestDispatcher("Cart.jsp").forward(request, response);
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
