let fs = require('fs');
let path = require('path');

function readJSON(filePath) {
  return new Promise((resolve, reject) => {
    fs.readFile(path.join(__dirname, filePath), 'utf8', (err, data) => {
      if (err){
        return reject('Failed to read file: ' + filePath);
      }   
      try {
        let json = JSON.parse(data);
        resolve(json);
      } catch {
          reject('Failed to parse JSON file: ' + filePath);
      }
    });
  });
}

function writeJSON(filePath, data) {
  return new Promise((resolve, reject) => {
    fs.writeFile(path.join(__dirname, filePath), JSON.stringify(data, null, 2), (err) => {
      if (err) {
        return reject('Failed to write file: ' + filePath);
      }
      resolve(true);
    });
  });
}


function paginate(data, start, end) {
  let totalCount = data.length;

  if (isNaN(start) || isNaN(end) || start < 0 || end <= 0 || start >= end) {
    return {
      paginatedData: [],
      totalCount: data.length,
      error: true,
      message: 'Invalid pagination parameters.'
    };
  }

 

  if (start >= totalCount) {
    return {
      paginatedData: [],
      totalCount,
      error: false,
      message: 'No data available for the given start and end.'
    };
  }

  let paginatedData = data.slice(start, end);
  return {
    paginatedData,
    totalCount,
    error: false,
    message: 'Paginated data fetched successfully.'
  };
}


function sortByField(data, field, order = 'asc') {
  return data.sort((a, b) => {
    let fieldA = a[field];
    let fieldB = b[field];

    if (field !== 'copies') {
      fieldA = String(fieldA || '').toLowerCase();
      fieldB = String(fieldB || '').toLowerCase();
    }

    if (fieldA < fieldB) {
        return order === 'asc' ? -1 : 1;
    }
    if (fieldA > fieldB) {
        return order === 'asc' ? 1 : -1;
    }
    return 0;
  });
}


function cleanString(str) {
  return (str && str !== 'null' && str !== 'undefined') ? str.toLowerCase().trim() : '';
}


module.exports = {
  readJSON,
  writeJSON,
  paginate,
  sortByField,
  cleanString
};
