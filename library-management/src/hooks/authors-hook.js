import {useState, useEffect, useRef} from "react";
import { getAuthors } from '../service/requestHandler';
import { useStatus } from './StatusContext';

function useAuthorsPage(){
    let { showStatus } = useStatus();
    let [authorsData, setAuthorsData] = useState(null);
    let [currentPageNumber , setcurrentPageNumber] = useState(1);
    let [totalAuthorsCount , setTotalAuthorsCount] = useState(0);
    let [searchString, setSearchString] = useState(null);
    let fetchLimit = 20;
    let fetchAuthors = (count,searchString)=>{
        count = count ? count : 1;
        let callback={
            success: (response) => {
                setcurrentPageNumber(count);
                setAuthorsData(response.data);
                setTotalAuthorsCount(response.data.totalAuthorsCount);
            },
            error: (err) => {
                showStatus('Failed to fetch authors data','error',1000);
                setAuthorsData(null);
            }
        };
        setSearchString(searchString);
        let startIndex = (count - 1) * fetchLimit;
        let endIndex = startIndex + fetchLimit;
        getAuthors({searchString,currentPageNumber:count-1 , startIndex, endIndex}, callback);
    };
    

    useEffect(() => {
        fetchAuthors(1,null);
    }, []);

    let debounceTimeout = useRef(null);

    let handleAuthorSearch = (e) =>{
        e.preventDefault();
        let searchValue = e.target.value.trim();

        if (debounceTimeout.current) {
            clearTimeout(debounceTimeout.current);
        }

        debounceTimeout.current = setTimeout(() => {
            if (searchValue.length) {
                if (searchValue !== searchString) {
                    fetchAuthors(1, searchValue);
                }
            } else {
                fetchAuthors(1, '');
            }
        }, 300); 
    };

    let handlePrevButtonClick = (e) => {
        e.preventDefault();
        if(currentPageNumber === 1){
            return;
        }
        fetchAuthors(currentPageNumber-1,searchString);
    }
    let handleNextButtonClick = (e) =>{
        e.preventDefault();
        if(totalAuthorsCount > (currentPageNumber * fetchLimit)){
            fetchAuthors(currentPageNumber+1,searchString);
        }
    }

    let isFirstPage = currentPageNumber === 1;
    let isLastPage = (totalAuthorsCount - (currentPageNumber * fetchLimit)) <= 0;
    
    return {fetchLimit, currentPageNumber, isFirstPage , isLastPage , authorsData, handlePrevButtonClick, handleNextButtonClick, handleAuthorSearch};
}

export default useAuthorsPage;