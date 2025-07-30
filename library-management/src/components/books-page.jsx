import '../styling/common.css';
import '../styling/books-page.css';
import NavbarComponent from './NavbarComponent';
import useBooksPage from '../hooks/books-hook';
import ConfirmationModal from "./confirmation-modal"

function BooksPage() {
    let {
        userRole, currentPageNumber,
        isFirstPage, isLastPage, booksData, sortConfig,
        handlePrevButtonClick, handleNextButtonClick, handleBookDelete, handleBookUpdate, handleSortButton, handleSearch,
        showConfirmation, message ,handleConfirm, handleCancel
    } = useBooksPage();

    return (
        <>
            <div className="ui-page-wrapper ">
                <NavbarComponent />
                <div className="ui-data-header ui-flex">Books Catalogue</div>
                <table className="ui-table">
                    <thead>
                        <tr>
                            <th><span>Title</span><button className="ui-sort-btn" onClick={(event)=>handleSortButton('Title',event)}>{sortConfig.column === 'Title' ? sortConfig.order : 'ASC'}</button><br />
                                <input className="ui-books-page-search-filter" type="text" name="searchTitle" onKeyUp={handleSearch} />
                            </th>
                            <th><span>Author</span><button className="ui-sort-btn" onClick={(event)=>handleSortButton('Author',event)}>{sortConfig.column === 'Author' ? sortConfig.order : 'ASC'}</button><br />
                                <input className="ui-books-page-search-filter" type="text" name="searchAuthor" onKeyUp={handleSearch} />
                            </th>
                            <th><span>ISBN</span></th>
                            <th><span>Genre</span><button className="ui-sort-btn" onClick={(event)=>handleSortButton('Genre',event)}>{sortConfig.column === 'Genre' ? sortConfig.order : 'ASC'}</button></th>
                            <th><span>Copies</span><button className="ui-sort-btn" onClick={(event)=>handleSortButton('Copies',event)}>{sortConfig.column === 'Copies' ? sortConfig.order : 'ASC'}</button></th>
                            {(userRole === "admin" || userRole === "librarian") && <th>Edit</th>}
                            {userRole === "admin" && <th>Discard</th>}
                        </tr>
                    </thead>
                        <>
                            <tbody>
                            {(!booksData || booksData.books.length === 0) ? (
                                <tr><td colSpan='10000' >No books found</td></tr>
                            ) : (
                                booksData.books.map((book, index) => (
                                    <tr key={index}>
                                        <td id="bookTitle">{book.title}</td>
                                        <td>{book.author}</td>
                                        <td>{book.isbn}</td>
                                        <td>{book.genre}</td>
                                        <td>{book.copies}</td>
                                        {(userRole === "admin" || userRole === "librarian") && <td ><button onClick={(event)=> handleBookUpdate(book,event)}>Update</button></td>}
                                        {userRole === "admin" && <td ><button onClick={(event)=>handleBookDelete(book,event)}>Delete</button></td>}
                                    </tr>
                                ))
                            )}
                            </tbody>
                        </>
                    
                </table>
                <div className="ui-footer ui-flex">
                    <span className="ui-footer-total ui-flex">
                        <label>Total books :</label>
                        <span className="ui-footer-total-count">{booksData?.totalBooksCount}</span>
                    </span>
                    <div className="ui-footer-buttons ui-flex">
                        <button className={`ui-button ${isFirstPage ? 'ui-btn-state-disabled' : ''}`} onClick={handlePrevButtonClick} >Prev</button>
                        <div className="ui-current-page-number">{currentPageNumber}</div>
                        <button className={`ui-button ${isLastPage ? 'ui-btn-state-disabled' : ''}`} onClick={handleNextButtonClick} >Next</button>
                    </div>
                </div>

            </div>
            {showConfirmation && (
                <ConfirmationModal
                    message={message}
                    handleCancel={handleCancel}
                    handleConfirm={handleConfirm}
                />
            )}

        </>
    );
}

export default BooksPage;