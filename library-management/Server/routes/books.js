const express = require('express');
const router = express.Router();
const isSessionActive = require('../middleware/auth');
const { readJSON, writeJSON, paginate, sortByField, cleanString } = require('../utils/util');

const booksDBFile = '../books.json';
const authorsDBFile = '../authors.json';


router.get('/', isSessionActive('canViewBooks'), async (req, res) => {
  try {
    let { titleSearchString, authorSearchString, currentPageNumber, startIndex, endIndex, sortOrder = 'asc', sortBy = 'title' } = req.query;

    currentPageNumber = parseInt(currentPageNumber);
    startIndex = parseInt(startIndex);
    endIndex = parseInt(endIndex);

    let books = await readJSON(booksDBFile);

    let filtered = books.filter(book =>
      (!titleSearchString || cleanString(book.title).startsWith(cleanString(titleSearchString))) &&
      (!authorSearchString || cleanString(book.author).startsWith(cleanString(authorSearchString)))
    );

    let sorted = sortByField(filtered, cleanString(sortBy), cleanString(sortOrder));
    let { paginatedData, totalCount, error, message } = paginate(sorted, startIndex, endIndex);

    if (error) {
      return res.status(400).json({
        success: false,
        message,
        error: 'Pagination Error',
        data: { books: [], totalBooksCount: totalCount }
      });
    }

    return res.status(200).json({
      success: true,
      message,
      data: { books: paginatedData, totalBooksCount: totalCount }
    });

  } catch (err) {
    return res.status(500).json({
      success: false,
      message: 'Failed to fetch searched books.',
      error: 'Server Error'
    });
  }
});


router.post('/', isSessionActive('canAddBooks'), async (req, res) => {
  try {
    let books = await readJSON(booksDBFile);
    let authors = await readJSON(authorsDBFile);
    let { title, author, isbn, genre, copies } = req.body;

    const isbnNumber = Number(isbn);
    const copiesNumber = Number(copies);
    const isbnCheck = /^\d{13}$/;

    let errorField = null;

    if (!title || title.trim().length === 0) errorField = 'Title';
    else if (!author || author.trim().length === 0) errorField = 'Author';
    else if (!isbnCheck.test(isbnNumber)) errorField = 'Isbn';
    else if (!genre || genre.trim().length === 0) errorField = 'Genre';
    else if (!Number.isInteger(copiesNumber) || copiesNumber <= 0) errorField = 'Copies';

    if (errorField) {
      return res.status(400).json({
        success: false,
        message: 'Invalid data provided.',
        error: `Invalid ${errorField}`,
        code: errorField
      });
    }

    const cleanAuthor = author.trim();
    let existingAuthor = authors.find(a => a.name.toLowerCase() === cleanAuthor.toLowerCase());
    let authorId;

    if (existingAuthor) {
      authorId = existingAuthor.id;
    } else {
      authorId = Math.max(0, ...authors.map(a => a.id)) + 1;
      authors.push({ id: authorId, name: cleanAuthor });
      await writeJSON(authorsDBFile, authors);
    }

      if (books.some(book => book.isbn === isbnNumber)) {
        return res.status(409).json({
          success: false,
          message: 'Duplicate book entry.',
          error: 'ISBN already exists',
          code: 'Duplicate Book'
        });
      }
      const id = Math.max(0, ...books.map(b => b.id)) + 1;
      books.push({ id, title, authorId, author: cleanAuthor, isbn: isbnNumber, genre, copies: copiesNumber });
 
    await writeJSON(booksDBFile, books);

    return res.status(200).json({
      success: true,
      message: `Book '${title}' saved successfully.`,
      data: { totalBooksCount: books.length }
    });

  } catch (err) {
    return res.status(500).json({
      success: false,
      message: 'Failed to save book.',
      error: 'Server Error',
      code: 'Internal Error'
    });
  }
});


router.patch('/:isbn', isSessionActive('canUpdateBooks'), async (req, res) => {
  try {
    const books = await readJSON(booksDBFile);
    const authors = await readJSON(authorsDBFile);
    const { title, author, genre, copies } = req.body;
    const isbnParam = req.params.isbn;

    const isbnCheck = /^\d{13}$/;
    const isbnNumber = Number(isbnParam);
    const copiesNumber = Number(copies);

    if (!isbnCheck.test(isbnNumber)) {
      return res.status(400).json({
        success: false,
        message: 'Invalid ISBN in URL.',
        error: 'Invalid ISBN format',
        code: 'Invalid Isbn'
      });
    }

    const bookIndex = books.findIndex(book => book.isbn === isbnNumber);
    if (bookIndex === -1) {
      return res.status(404).json({
        success: false,
        message: 'Book not found.',
        error: 'Book Not Found',
        code: 'Book Not Found'
      });
    }

    let errorField = null;
    if (!title || title.trim().length === 0) errorField = 'Title';
    else if (!author || author.trim().length === 0) errorField = 'Author';
    else if (!genre || genre.trim().length === 0) errorField = 'Genre';
    else if (!Number.isInteger(copiesNumber) || copiesNumber <= 0) errorField = 'Copies';

    if (errorField) {
      return res.status(400).json({
        success: false,
        message: 'Invalid data provided.',
        error: `Invalid ${errorField}`,
        code: errorField
      });
    }

    const cleanAuthor = author.trim();
    let existingAuthor = authors.find(a => a.name.toLowerCase() === cleanAuthor.toLowerCase());
    let authorId;

    if (existingAuthor) {
      authorId = existingAuthor.id;
    } else {
      authorId = Math.max(0, ...authors.map(a => a.id)) + 1;
      authors.push({ id: authorId, name: cleanAuthor });
      await writeJSON(authorsDBFile, authors);
    }

    books[bookIndex] = {
      ...books[bookIndex],
      title,
      author: cleanAuthor,
      authorId,
      genre,
      copies: copiesNumber
    };

    await writeJSON(booksDBFile, books);

    return res.status(200).json({
      success: true,
      message: `Book '${title}' updated successfully.`,
      data: { updatedBook: books[bookIndex] }
    });

  } catch (err) {
    return res.status(500).json({
      success: false,
      message: 'Failed to update book.',
      error: 'Server Error',
      code: 'Internal Error'
    });
  }
});




router.delete('/', isSessionActive('canDeleteBooks'), async (req, res) => {
  try {
    let books = await readJSON(booksDBFile);
    let { id } = req.query;
    id = Number(id);

    let index = books.findIndex(book => book.id === id);

    if (index === -1) {
      return res.status(404).json({
        success: false,
        message: 'Book not found.',
        error: 'Invalid Book ID',
        code: 'Book Not Found'
      });
    }

    books.splice(index, 1);
    await writeJSON(booksDBFile, books);

    return res.status(200).json({
      success: true,
      message: 'Book deleted successfully.',
      data: { totalBooksCount: books.length }
    });

  } catch (err) {
    return res.status(500).json({
      success: false,
      message: 'Failed to delete book.',
      error: 'Server Error',
      code: 'Internal Error'
    });
  }
});

module.exports = router;
