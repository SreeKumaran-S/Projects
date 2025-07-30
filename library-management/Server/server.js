let express = require('express');
let session = require('express-session');
let cors = require('cors');
let path = require('path');

let app = express();
let PORT = process.env.PORT || 4000;

app.use(express.static(path.join(__dirname, '../build')));

app.use(cors({ 
  origin: process.env.NODE_ENV === 'production' ? false : 'http://localhost:3000', 
  credentials: true 
}));

app.use(express.json());
app.use(session({
  secret: 'super-secret-key',
  resave: false,
  saveUninitialized: false,
  cookie: { maxAge: 30 * 60 * 1000 }
}));

let booksRoutes = require('./routes/books');
let authorsRoutes = require('./routes/authors');
let usersRoutes = require('./routes/users');

app.use('/books', booksRoutes);
app.use('/authors', authorsRoutes);
app.use('/users', usersRoutes);


app.get('*', (req, res) => {
  res.sendFile(path.join(__dirname, '../build', 'index.html'));
});

app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
