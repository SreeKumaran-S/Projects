var close=document.getElementById('close');
var menu=document.getElementById('menu');
var side_nav=document.querySelector('.side-nav');

close.addEventListener("click",function(){
    side_nav.style.left="-50%";
});
menu.addEventListener("click",function(){
    side_nav.style.left="0%";
});

//search-side

var allProd=document.querySelectorAll('.prod');

var search=document.querySelector('.search').querySelector('div').querySelector('input');

search.addEventListener('keyup',function(){
   var value=search.value;
   value=value.toUpperCase();
   for(i=0;i<allProd.length;i++){
     if(allProd[i].innerText.toUpperCase().indexOf(value)<0)
       allProd[i].style.display="none";
     else
       allProd[i].style.display="flex";
    }
   
});
