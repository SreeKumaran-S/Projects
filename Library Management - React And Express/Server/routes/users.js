const express = require('express');
const router = express.Router();
const isSessionActive = require('../middleware/auth');
const { readJSON, writeJSON, paginate, sortByField, cleanString } = require('../utils/util');

const usersDBFile = '../users.json';

router.get('/validateUser', async (req, res) => {
    try {
      const users = await readJSON(usersDBFile);
      const { username, password } = req.query;

      if (!username || !password) {
        return res.status(400).json({
          success: false,
          message: 'Username and password are required.',
          error: 'Username and password are required.',
          code: 'MISSING_CREDENTIALS'
        });
      }
  
      const user = users.find(u => u.username === username);
  
      if (!user) {
        return res.status(404).json({
          success: false,
          message: 'Invalid username.',
          error: 'Invalid username.',
          code: 'USERNAME_NOT_FOUND'
        });
      }
  
      if (user.password !== password) {
        return res.status(401).json({
          success: false,
          message: 'Incorrect password.',
          error: 'Incorrect password.',
          code: 'WRONG_PASSWORD'
        });
      }
  
      req.session.user = { id: user.id, role: user.role };
      return res.status(200).json({
        success: true,
        message: 'User validated successfully.',
        data: { username: user.username }
      });
  
    } catch (err) {
      return res.status(500).json({
        success: false,
        message: 'Internal server error while validating user.',
        error: 'Internal server error while validating user.',
        code: 'SERVER_ERROR'
      });
    }
  });
  
  
router.post('/logout', (req, res) => {
    if (!req.session) {
      res.clearCookie('connect.sid');
      return res.status(200).json({
        success: true,
        message: 'No session to clear, but logout successful.'
      });
    }
  
    req.session.destroy((err) => {
      if (err) {
        return res.status(500).json({
          success: false,
          message: 'Logout failed.',
          error: 'Session destroy error',
          code: 'LOGOUT_ERROR'
        });
      }
  
      res.clearCookie('connect.sid');
      return res.status(200).json({
        success: true,
        message: 'Logged out successfully.'
      });
    });
  });
  
router.get('/getPermissions', isSessionActive(), (req, res) => {
    res.json({
      data: {
        role: req.userRole,
        permissions: req.userPermissions
      },
      success: true,
      message: "User permission data fetched successfully"
    });
  });

module.exports = router;