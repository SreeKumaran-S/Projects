window.onload = function () {
    let roamEle = document.getElementById('roamingImage');

    let imageHeight =  parseFloat(getComputedStyle(roamEle).height);
    let imageWidth = parseFloat(getComputedStyle(roamEle).width);
    let bottomLimit = window.innerHeight;
    let rightLimit = window.innerWidth;

    let reachedTop = false;
    let reachedBottom = false;
    let reachedLeft = false;
    let reachedRight = false;
    
    let speed = 10000000;       // NEXT IT WILL BE GOING TO  SUPER SAYAN .....
    let frames = 1;
   
    
    setInterval(()=>{
        bottomLimit = window.innerHeight;
        rightLimit = window.innerWidth;
        let left = parseFloat(getComputedStyle(roamEle).left);
        let top = parseFloat(getComputedStyle(roamEle).top);

        if(top + imageHeight  >= bottomLimit ){
            reachedBottom = true;
            reachedTop = false;
        }
        if(top <= 0){
            reachedTop = true;
            reachedBottom = false;
        }
        if(left <= 0){
            reachedLeft = true;
            reachedRight = false;
        }
        if(left + imageWidth >= rightLimit){
            reachedRight = true;
            reachedLeft = false;
        }
       
        if(reachedTop){
            roamEle.style.top = `${top + speed}px`;
            roamEle.style.left = `${reachedRight ? left - speed : left + speed}px`;
        }

        if(reachedBottom){
            roamEle.style.top = `${top - speed}px`;
            roamEle.style.left = `${reachedRight ? left - speed : left + speed}px`;
        }
    },frames);
}


