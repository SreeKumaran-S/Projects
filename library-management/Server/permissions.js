module.exports = {
    admin: {
      canViewBooks: true,
      canAddBooks: true,
      canUpdateBooks: true,
      canDeleteBooks: true,
      canViewAuthors: true
    },
    librarian: {
      canViewBooks: true,
      canAddBooks: true,
      canUpdateBooks: true,
      canDeleteBooks: false,
      canViewAuthors : true
    },
    guest: {
      canViewBooks: true,
      canAddBooks: false,
      canUpdateBooks: false,
      canDeleteBooks: false,
      canViewAuthors : true
    }
};

  