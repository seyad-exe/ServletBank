package com.banking.api.controller;

import com.banking.api.model.Transaction;
import com.banking.api.service.TransactionService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/transactions")
public class TransactionHistoryServlet extends HttpServlet {

    private TransactionService transactionService;

    @Override
    public void init() { 
    	this.transactionService = new TransactionService(); 
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("username");

        // default to page 1 size 5
        int page = 1;
        int size = 5;
        
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("size") != null) {
                size = Integer.parseInt(request.getParameter("size"));
            }
        } catch (NumberFormatException e) {
            // Ignore bad numbers and just use defaults
        }

        List<Transaction> history = transactionService.getUserTransactions(username, page, size);

        response.setContentType("text/plain");
        PrintWriter out = response.getWriter();
        
        out.println("Transaction History (Page " + page + "):");
        out.println("-------------------------------------------------");
        
        if (history.isEmpty()) {
            out.println("No transactions found.");
        } else {
            for (Transaction t : history) {
                out.println(String.format("[%s] %s | %s | $%.2f", 
                        t.date.toString(), t.accountNumber, t.type, t.amount));
            }
        }
    }
}