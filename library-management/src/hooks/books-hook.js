import { useState, useEffect, useRef} from 'react';
import { getBooks , deleteBook } from '../service/requestHandler';
import { useNavigate } from "react-router-dom";
import { useStatus } from './StatusContext';

function useBooksPage() {
    let debounceTimeout = useRef(null);
    let navigate = useNavigate(); 
    let { showStatus } = useStatus();
    
    let userRole = localStorage.getItem('role');

    let [booksData, setBooksData] = useState(null);
    let [currentPageNumber , setcurrentPageNumber] = useState(1);
    let [totalBooksCount , setTotalBooksCount] = useState(0);
    let [sortConfig, setSortConfig] = useState({
        column: 'Title',
        order: 'ASC'
    });
    let [searchColumnsData,setSearchColumnsData]= useState({
        titleSearchString : null,
        authorSearchString : null
    });
    
    let [message, setMessage] = useState('');
    let [showConfirmation, setShowConfirmation] = useState(false);
    let [bookToDelete, setBookToDelete] = useState(null);
    
    
    useEffect(() => {
        if (!localStorage.getItem('loginStatusOnce')) {
            showStatus('You logged in Successfully', 'info');
            localStorage.setItem('loginStatusOnce', 'true');
        }
        else if (localStorage.getItem('bookUploadOnce')) {
            showStatus('Book uploaded Successfully', 'success');
            localStorage.removeItem('bookUploadOnce');
        }
    }, [showStatus]);
    
    let fetchLimit = 20;
    let fetchBooks =  (count , titleSearchString = searchColumnsData.titleSearchString, authorSearchString = searchColumnsData.authorSearchString,  customSortBy = sortConfig.column, customSortOrder = sortConfig.order)=>{
        count = count ? count : 1;
        let callback={
            success: (response) => {
                setcurrentPageNumber(count);
                setBooksData(response.data);
                setTotalBooksCount(response.data.totalBooksCount);
            },
            error: (err) => {
                showStatus('Failed to fetch books','error',1000);
                if(err.error === 'Invalid route'){
                    navigate('/error');
                }
                setBooksData(null);
            }
        };

        let startIndex = (count - 1) * fetchLimit;
        let endIndex = startIndex + fetchLimit;   
        getBooks({titleSearchString,authorSearchString, currentPageNumber: count - 1, startIndex , endIndex,  sortOrder : customSortOrder , sortBy: customSortBy }, callback);    
    };

    // eslint-disable-next-line react-hooks/exhaustive-deps
    useEffect(() => {
        fetchBooks(1);
    },[] );

    let handlePrevButtonClick = (e) => {
        e.preventDefault();
        if(currentPageNumber === 1){
            return;
        }
        fetchBooks(currentPageNumber - 1);
    }
    let handleNextButtonClick = (e) =>{
        e.preventDefault();
        if(totalBooksCount > (currentPageNumber * fetchLimit)){
            fetchBooks(currentPageNumber + 1);
        }
    }
    const handleBookDelete = (bookData,e) => {
        e.preventDefault();
        setBookToDelete(bookData);
        setMessage(`Are you sure you want to delete ?`);
        setShowConfirmation(true);
    };
    const handleCancel = () => {
        setShowConfirmation(false);
        setBookToDelete(null);
        setMessage('');
    };

    const handleConfirm = () => {
        const callback = {
            success: (response) => {
                let updatedCount = response.data.totalBooksCount;
                const totalPages = Math.ceil(updatedCount / fetchLimit);
                let newPage = currentPageNumber;
                if (currentPageNumber > totalPages) {
                    newPage = totalPages;
                }
                fetchBooks(newPage);
            },
            error: (error) => {
                
            }
        };
        deleteBook({id:bookToDelete.id}, callback);
        setShowConfirmation(false);
        setBookToDelete(null);
        setMessage('');
    };
    
    let handleBookUpdate = (bookData,e)=>{
        e.preventDefault();
        navigate('/updateBook', {state: { bookData } });
    }

    let handleSortButton = (column,e)=>{
        e.preventDefault();
        let oldOrder = e.target.textContent;
        let newOrder = 'ASC';
        if(oldOrder === 'ASC'){
            newOrder = 'DESC';
        }
        setSortConfig({ column, order: newOrder });

        fetchBooks(currentPageNumber , searchColumnsData.titleSearchString, searchColumnsData.authorSearchString , column , newOrder);
    };
    let handleSearch = (e) =>{
        e.preventDefault();
        let searchValue = e.target.value.trim();
        let searchColumn = e.target.name;
        
        if (debounceTimeout.current) {
            clearTimeout(debounceTimeout.current);
        }
        if (searchColumn === 'searchTitle') {
            setSearchColumnsData(prevData => ({
                ...prevData,
                titleSearchString: searchValue
            }));
        } else if (searchColumn === 'searchAuthor') {
            setSearchColumnsData(prevData => ({
                ...prevData,
                authorSearchString: searchValue
            }));
        }

        debounceTimeout.current = setTimeout(() => {
            if (searchColumn === 'searchTitle') {
                fetchBooks(1, searchValue, searchColumnsData.authorSearchString);
            } else if (searchColumn === 'searchAuthor') {
                fetchBooks(1, searchColumnsData.titleSearchString, searchValue);
            }
        }, 300);
    };
    

    let isFirstPage = currentPageNumber === 1;
    let isLastPage = (totalBooksCount - (currentPageNumber * fetchLimit)) <= 0;
    return {userRole, currentPageNumber, isFirstPage , isLastPage , booksData, sortConfig, handlePrevButtonClick, handleNextButtonClick, handleBookDelete ,handleBookUpdate,handleSortButton,handleSearch,showConfirmation, message ,handleConfirm, handleCancel};
}

export default useBooksPage;
