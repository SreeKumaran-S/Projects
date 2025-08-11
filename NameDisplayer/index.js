window.onload = function () {
    let displayEle = document.querySelector('#nameContainer');

    let dotAlphabet = {
        'a': [
            ' ..  ',
            '.  . ',
            '.... ',
            '.  . ',
            '.  . '
        ],
        'b': [
            '...  ',
            '.  . ',
            '...  ',
            '.  . ',
            '...  '
        ],
        'c': [
            ' ..  ',
            '.  . ',
            '.    ',
            '.  . ',
            ' ..  '
        ],
        'd': [
            '...  ',
            '.  . ',
            '.  . ',
            '.  . ',
            '...  '
        ],
        'e': [
            '.... ',
            '.    ',
            '...  ',
            '.    ',
            '.... '
        ],
        'f': [
            '.... ',
            '.    ',
            '...  ',
            '.    ',
            '.    '
        ],
        'g': [
            ' ..  ',
            '.    ',
            '. .. ',
            '.  . ',
            ' ..  '
        ],
        'h': [
            '.  . ',
            '.  . ',
            '.... ',
            '.  . ',
            '.  . '
        ],
        'i': [
            '.... ',
            ' ..  ',
            ' ..  ',
            ' ..  ',
            '.... '
        ],
        'j': [
            '.... ',
            '   . ',
            '   . ',
            '.  . ',
            ' ..  '
        ],
        'k': [
            '.  . ',
            '. .  ',
            '..   ',
            '. .  ',
            '.  . '
        ],
        'l': [
            '.    ',
            '.    ',
            '.    ',
            '.    ',
            '.... '
        ],
        'm': [
            '.   .',
            '.. ..',
            '. . .',
            '.   .',
            '.   .'
        ],
        'n': [
            '.   .',
            '..  .',
            '. . .',
            '.  ..',
            '.   .'
        ],
        'o': [
            ' ..  ',
            '.  . ',
            '.  . ',
            '.  . ',
            ' ..  '
        ],
        'p': [
            '...  ',
            '.  . ',
            '...  ',
            '.    ',
            '.    '
        ],
        'q': [
            ' ..  ',
            '.  . ',
            '.  . ',
            ' ..  ',
            '   . '
        ],
        'r': [
            '...  ',
            '.  . ',
            '...  ',
            '. .  ',
            '.  . '
        ],
        's': [
            ' ....',
            '.    ',
            ' ... ',
            '    .',
            '.... '
        ],
        't': [
            '.....',
            '  .  ',
            '  .  ',
            '  .  ',
            '  .  '
        ],
        'u': [
            '.    . ',
            '.    . ',
            '.    . ',
            '.    . ',
            '...... '
        ],
        'v': [
            '.   .',
            '.   .',
            '.   .',
            ' . . ',
            '  .  '
        ],
        'w': [
            '.   .',
            '.   .',
            '. . .',
            '.. ..',
            '.   .'
        ],
        'x': [
            '.   .',
            ' . . ',
            '  .  ',
            ' . . ',
            '.   .'
        ],
        'y': [
            '.   .',
            ' . . ',
            '  .  ',
            '  .  ',
            '  .  '
        ],
        'z': [
            '.....',
            '   . ',
            '  .  ',
            ' .   ',
            '.....'
        ]
    };

  function printDotArtWordDotByDot(word, displayEle, scale = 2, speed = 5) {
    word = word.toLowerCase();
    const lines = [];

    
    for (let i = 0; i < 5 * scale; i++) {
        lines.push('');
    }

    for (let char of word) {
        if (dotAlphabet[char]) {
            for (let row = 0; row < 5; row++) {
                let scaledRow = '';
                for (let colChar of dotAlphabet[char][row]) {
                    scaledRow += colChar.repeat(scale);
                }
                for (let s = 0; s < scale; s++) {
                    lines[row * scale + s] += scaledRow + '  ';
                }
            }
        } else {
            for (let row = 0; row < 5 * scale; row++) {
                lines[row] += ' '.repeat(5 * scale) + '  ';
            }
        }
    }

   
    let displayLines = Array(lines.length).fill('');
    let row = 0;
    let col = 0;

    const interval = setInterval(() => {
        if (row >= lines.length) {
            clearInterval(interval);
            return;
        }

        
        displayLines[row] += lines[row][col] || ' ';

        
        displayEle.textContent = displayLines.join('\n');

      
        row++;
        if (row === lines.length) {
            row = 0;
            col++;
        }

        
        if (col >= lines[0].length) {
            clearInterval(interval);
        }
    }, speed);
}


    printDotArtWordDotByDot('PERSEVERANCE', displayEle , 2, 1);
};