import { useLocation } from 'react-router-dom';
import  '../styling/book-form-page.css';
import  '../styling/common.css';
import NavbarComponent from './NavbarComponent';
import  useBookFormPage   from '../hooks/book-form-hook';
import SelectComponent from "./select-component";

function BookFormPage(){
    let location = useLocation();
    let pageName = location.pathname.split('/')[1];
    let isUpdatePage = pageName === 'updateBook';
    let buttonName = isUpdatePage ? 'Update' : 'Add';
    let bookData= location.state?.bookData || null; 
    let {
        warning,setTitle,setIsbn,setGenre,setCopies,setSelectedAuthor,
        title, isbn, copies, genre,
        genres,authors,selectedAuthor,isAuthorDialogOpen,
        handleBookSubmit,handleSelectAuthorClick,handleAuthorSearch,handleSelectAuthor
    } = useBookFormPage({bookData});
    return(
        <div className="ui-books-container ui-flex" >
            <NavbarComponent />
            <h1>Book Form</h1>
            <form onSubmit={handleBookSubmit}>
                <div className="ui-books-row-entity ui-flex">
                    <label className="ui-title">Title<span style={{ color: "red" }}>*</span></label>
                    <div className='ui-flex'>
                    <input className="ui-value-entity" type="text" defaultValue={title} onChange={(e) => setTitle(e.target.value)}  /> 
                    {warning?.titleWarn && (
                      <span className="ui-warning-text ui-invalid-value">{warning?.titleWarn}</span>
                    )}
                    </div>
                </div>
                <div className="ui-books-row-entity ui-flex">
                    <label className="ui-title">Author<span style={{ color: "red" }}>*</span></label>
                    <div className='ui-flex'>
                    <input className="ui-value-entity" type="text" value={selectedAuthor} onChange={(e) => setSelectedAuthor(e.target.value)}  placeholder="no author selected"  />
                    {warning?.authorWarn && (
                      <span className="ui-warning-text ui-invalid-value">{warning.authorWarn}</span>
                    )}
                    </div>
                    <button type="button" onClick={handleSelectAuthorClick}>Select Author</button>    
                    <SelectComponent
                        authorNames={authors}
                        isAuthorDialogOpen={isAuthorDialogOpen}
                        handleAuthorSearch={handleAuthorSearch}
                        handleSelectAuthor={handleSelectAuthor}
                    />
                </div>
                <div className="ui-books-row-entity ui-flex">
                    <label className="ui-title">ISBN<span style={{ color: "red" }}>*</span></label>
                    <div className='ui-flex'>
                    <input className="ui-value-entity" type="text" defaultValue={isbn} onChange={(e) => setIsbn(e.target.value)} />
                    {warning?.isbnWarn && (
                      <span className="ui-warning-text ui-invalid-value">{warning.isbnWarn}</span>
                    )}
                    </div>
                </div>
                <div className="ui-books-row-entity ui-flex">
                    <label className="ui-title">Genre</label>

                    <select value={genre} onChange={(e) => setGenre(e.target.value)}>
                    {genres.map((genre,index)=>(
                          <option key={genre} value={genre}>
                            {genre}
                          </option>
                    ))}
                    </select>
                </div>
                <div className="ui-books-row-entity ui-flex">
                    <label className="ui-title">Copies</label>
                    <input className="ui-value-entity" type="number"  defaultValue={copies} onChange={(e) => {const value = Number(e.target.value); setCopies(value < 1 ? 1 : value);}}/>
                </div>
                <div className="ui-button-controls ui-flex">
                    <button className="ui-submit" type="submit">{buttonName}</button>
                </div>
            </form>
        </div>
    );
}
export default BookFormPage;