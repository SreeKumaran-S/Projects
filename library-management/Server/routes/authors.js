const express = require('express');
const router = express.Router();
const isSessionActive = require('../middleware/auth');
const { readJSON, paginate, cleanString } = require('../utils/util');

const authorsDBFile = '../authors.json';

router.get('/', isSessionActive('canViewAuthors'), async (req, res) => {
  try {
    let authors = await readJSON(authorsDBFile);
    let { searchString = '', currentPageNumber, startIndex, endIndex } = req.query;

    searchString = cleanString(searchString);
    currentPageNumber = parseInt(currentPageNumber);
    startIndex = parseInt(startIndex);
    endIndex = parseInt(endIndex);

   
    if (Number.isNaN(currentPageNumber)) {
      return res.status(200).json({
        success: true,
        message: 'Authors fetched without pagination.',
        data: { authors }
      });
    }

    
    let filtered = searchString.length > 0
      ? authors.filter(a => cleanString(a.name).includes(searchString))
      : authors;

    
    let { paginatedData, totalCount, error, message } = paginate(filtered, startIndex, endIndex);

    if (error) {
      return res.status(400).json({
        success: false,
        message: message || 'Invalid pagination parameters.',
        error: 'Pagination Error',
        data: { authors: [], totalAuthorsCount: totalCount }
      });
    }

    
    return res.status(200).json({
      success: true,
      message: message || 'Authors fetched successfully.',
      data: { authors: paginatedData, totalAuthorsCount: totalCount }
    });

  } catch (err) {
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch authors.',
      error: 'Server Error'
    });
  }
});

module.exports = router;
