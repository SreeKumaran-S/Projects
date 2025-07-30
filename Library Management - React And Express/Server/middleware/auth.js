const permissions = require('../permissions');

const isSessionActive = (permissionKey) => {
  return (req, res, next) => {
    const role = req.session?.user?.role;

    if (!role) {
      return res.status(401).json({
        error: 'Unauthorized',
        message: 'User session not active. Please log in.'
      });
    }

    const rolePermissions = permissions[role];

    if (permissionKey) {
      if (!rolePermissions || !rolePermissions[permissionKey]) {
        return res.status(403).json({
          error: 'Forbidden',
          code: 'PERMISSION INVALID',
          message: 'You do not have permission to perform this action.'
        });
      }
    } else {
      req.userRole = role;
      req.userPermissions = rolePermissions;
    }

    next();
  };
};

module.exports = isSessionActive;
