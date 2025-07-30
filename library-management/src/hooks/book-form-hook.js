import { useState, useEffect } from 'react';
import { getAuthors , uploadBook , updateBook } from '../service/requestHandler';
import { GENRES } from "../service/constants";
import { useNavigate, useLocation } from 'react-router-dom';
import { useStatus } from './StatusContext';

function useBookFormPage({bookData}={}){
    let { showStatus } = useStatus();

    if (localStorage.getItem('bookUploadOnce')) {
        showStatus('Book uploaded Successfully', 'success');
        localStorage.removeItem('bookUploadOnce');
    }
    let location = useLocation();
    let navigate = useNavigate(); 
    let genres = GENRES;
   
    
    let [authorNames , setAuthorNames] = useState(null);
    let [searchStringAuthorNames, setSearchStringAuthorNames] = useState(null);
    let [selectedAuthor,setSelectedAuthor] = useState(bookData?.author || '');
    let [isAuthorDialogOpen, setAuthorDialogOpen] = useState(false);
    let [title,setTitle]= useState(bookData?.title || '');
    let [isbn,setIsbn]= useState(bookData?.isbn || '');
    let [genre,setGenre]= useState(bookData?.genre || genres[0]);
    let [copies,setCopies]= useState(bookData?.copies || 1);
    let authors = searchStringAuthorNames;
    let [warning, setWarning] = useState({
        titleWarn:null,
        authorWarn:null,
        isbnWarn:null
      });

    useEffect(()=>{
        fetchAuthors();
    },[]);

    let fetchAuthors = ()=>{
        let callback={
            success: (response) => {
                setAuthorNames(response.data);
                setSearchStringAuthorNames(response.data);
            },
            error: (err) => {         
            }
        };
        getAuthors({}, callback);   
    };
    let handleAuthorSearch = (e) =>{
        e.preventDefault();
        let searchValue = e.target.value.trim();
        let newSearchArray = {authors:[]};
        authorNames.authors.forEach((author,index)=>{
            if(author.name.includes(searchValue)){
                newSearchArray.authors.push(author);
            }
        });
        setSearchStringAuthorNames(newSearchArray);
    };
    let handleSelectAuthor = (e)=>{
        e.preventDefault();
        let selectedAuthorName = e.target.textContent;
        setSelectedAuthor(selectedAuthorName);
        setAuthorDialogOpen(false);
    };
    

    let handleBookSubmit = (e)=>{
        e.preventDefault();
        let uploadTitle = title;
        let uploadAuthor = selectedAuthor;
        let uploadIsbn = isbn;
        let uploadGenre = genre;
        let uploadCopies = copies;

        uploadIsbn = Number(uploadIsbn); 
        uploadCopies = Number(uploadCopies); 
        let isbnCheck = /^\d{13}$/;
      
        let errorName ;

        if (!uploadTitle || uploadTitle.trim().length === 0) errorName = 'Title';
        else if (!uploadAuthor || uploadAuthor.trim().length === 0) errorName = 'Author';
        else if (!isbnCheck.test(uploadIsbn)) 
            {errorName = 'Isbn';}
        else if (!uploadGenre || uploadGenre.trim().length === 0) errorName = 'Genre';
        else if (!Number.isInteger(uploadCopies) || uploadCopies <= 0) errorName = 'Copies';

        if (errorName) {
            if (errorName === 'Title') {
                setWarning({ titleWarn: 'Enter valid title' })
            }
            else if (errorName === 'Author') {
                setWarning({ authorWarn: 'Enter valid author name' })
            }
            else if (errorName === 'Isbn') {
                setWarning({ isbnWarn: 'Enter valid isbn number' })
            }
            else if (errorName === 'Genre') {
                setWarning({ genreWarn: 'Enter valid genre' })
            }
            else if (errorName === 'Copies') {
                setWarning({ copiesWarn: 'Enter valid number of copies' })
            }
        }

        else{
            let pageName = location.pathname.split('/')[1];
            let callback = {
                success:()=>{
                    localStorage.setItem('bookUploadOnce','true');
                    if(pageName === 'addBook'){
                        window.location.reload();
                    }
                    else{
                        navigate('/books');
                    }
                },
                error:(error)=>{
                    let errorCode = error.code;
                    if (errorCode) {
                        if (errorCode === 'Title') {
                            setWarning({ titleWarn: 'Enter valid title' })
                        }
                        else if (errorCode === 'Author') {
                            setWarning({ authorWarn: 'Enter valid author name' })
                        }
                        else if (errorCode === 'Isbn') {
                            setWarning({ isbnWarn: 'Enter valid isbn number' })
                        }
                        else if (errorCode === 'Genre') {
                            setWarning({ genreWarn: 'Enter valid genre' })
                        }
                        else if (errorCode === 'Copies') {
                            setWarning({ copiesWarn: 'Enter valid number of copies' })
                        }
                        showStatus("Failed to upload data, "+ errorCode , 'error', 3000);
                    }
                   else{
                     showStatus("Error occured", 'error', 1000);
                   }
                    
                }
            
            }
            if(pageName === 'addBook'){
                uploadBook({title:uploadTitle, author:uploadAuthor, isbn:uploadIsbn, genre:uploadGenre, copies:uploadCopies}, callback);
            }
            else{
                updateBook(uploadIsbn, {title:uploadTitle, author:uploadAuthor, genre:uploadGenre, copies:uploadCopies}, callback);
            }
            
        }
    };
   
   
    let handleSelectAuthorClick = (e)=>{
        e.preventDefault();
        if(isAuthorDialogOpen){
            setAuthorDialogOpen(false);
        }
        else{
            setAuthorDialogOpen(true);
        }
    };
    return{
        warning,setTitle ,setIsbn,setGenre,setCopies,setSelectedAuthor,
        title, isbn, copies, genre,
        genres ,authors, selectedAuthor,isAuthorDialogOpen,
        handleBookSubmit,handleSelectAuthorClick,handleAuthorSearch,handleSelectAuthor
    };
}
export default useBookFormPage;