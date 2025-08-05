package controller;

import model.Allocation;
import service.AllocationService;
import utils.ResponseUtil;

import com.google.gson.Gson;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class AllocationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            String jsonString = sb.toString().trim();
            if (jsonString.isEmpty()) {
                ResponseUtil.badRequest(resp, "Request body is empty");
                return;
            }
            
            Gson gson = new Gson();
            Allocation allocation;
            try {
                allocation = gson.fromJson(jsonString, Allocation.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (allocation == null) {
                ResponseUtil.badRequest(resp, "Failed to parse allocation data");
                return;
            }

            AllocationService allocationService = new AllocationService();
            allocationService.borrowBook(allocation);
            ResponseUtil.sendSuccess(resp, "Book borrowed successfully");
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid number format in input");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error borrowing book: " + e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo != null && pathInfo.length() > 1) {
            try {
                int allocationId = Integer.parseInt(pathInfo.substring(1));
                AllocationService allocationService = new AllocationService();
                Allocation allocation = allocationService.getAllocationById(allocationId);
                ResponseUtil.sendSuccess(resp, allocation);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid allocation ID format");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching allocation");
            }
        } else {
            String startIndexParam = req.getParameter("startIndex");
            String limitParam = req.getParameter("limit");
            int startIndex = 0;
            int limit = 10;

            try {
                if (startIndexParam != null)
                    startIndex = Integer.parseInt(startIndexParam);
                if (limitParam != null)
                    limit = Integer.parseInt(limitParam);

                AllocationService allocationService = new AllocationService();
                List<Allocation> allocations = allocationService.getAllAllocationsPaginated(startIndex, limit);

                ResponseUtil.sendSuccess(resp, allocations);
            } catch (NumberFormatException e) {
                ResponseUtil.badRequest(resp, "Invalid pagination parameters");
            } catch (IllegalArgumentException e) {
                ResponseUtil.badRequest(resp, e.getMessage());
            } catch (Exception e) {
                ResponseUtil.serverError(resp, "Error fetching allocations");
            }
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        
        if (pathInfo == null || pathInfo.length() <= 1) {
            ResponseUtil.badRequest(resp, "Allocation ID is required");
            return;
        }
        
        try {
            int allocationId = Integer.parseInt(pathInfo.substring(1));
            
            StringBuilder sb = new StringBuilder();
            String line;
            try (BufferedReader reader = req.getReader()) {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            }
            
            String jsonString = sb.toString().trim();
            if (jsonString.isEmpty()) {
                ResponseUtil.badRequest(resp, "Request body is empty");
                return;
            }
            
            Gson gson = new Gson();
            Allocation allocation;
            try {
                allocation = gson.fromJson(jsonString, Allocation.class);
            } catch (Exception e) {
                ResponseUtil.badRequest(resp, "Invalid JSON format: " + e.getMessage());
                return;
            }
            
            if (allocation == null) {
                ResponseUtil.badRequest(resp, "Failed to parse allocation data");
                return;
            }
            
            allocation.setId(allocationId);
            
            AllocationService allocationService = new AllocationService();
            Allocation returnedAllocation = allocationService.returnBook(allocation);
            
            String message = "Book returned successfully";
            if (returnedAllocation.getFineAmount() > 0) {
                message += ". Fine amount: ₹" + returnedAllocation.getFineAmount();
            }
            
            ResponseUtil.sendSuccess(resp, message);
            
        } catch (NumberFormatException e) {
            ResponseUtil.badRequest(resp, "Invalid allocation ID format");
        } catch (IllegalArgumentException e) {
            ResponseUtil.badRequest(resp, e.getMessage());
        } catch (Exception e) {
            ResponseUtil.serverError(resp, "Error returning book: " + e.getMessage());
        }
    }
} 