# Sportify
### Sportify app is a store to buy sport equipments
### Category
<img src ="https://github.com/Abodi-Massarweh/Sportify/blob/cdfea674b26035cf0e4345a06c2fc42d63c25841/Categories.jpeg" width="40%" height="500" >

### Explore
<img src="https://github.com/Abodi-Massarweh/Sportify/blob/cdfea674b26035cf0e4345a06c2fc42d63c25841/Explore.jpeg" width="40%" height="500" >

### Firebase Database
#### Logging in
<img src="https://github.com/Abodi-Massarweh/Sportify/blob/0310380634148eb359ecbbde7d21fd2eec81f845/Logging%20In.jpeg" width="40%" height="500" >


### Profile 
<img src="https://github.com/Abodi-Massarwa/Sportify/blob/master/Screen%20Shot%202022-03-01%20at%2023.30.55.png" width="40%" height="500" >

### Navigation Bar 
 <img width="40%" alt="NavigationBar" height="500" src="https://user-images.githubusercontent.com/58775369/156252756-536c19a0-ff00-4112-b16d-daf532c8831c.png">
 

### Another images 
 <img width="40%" alt="NavigationBar" height="500" src="https://user-images.githubusercontent.com/58775369/156253173-2b27f8ca-567f-431c-a925-7eecd0229335.png">
 <img width="40%" alt="NavigationBar" height="500" src="https://user-images.githubusercontent.com/58775369/156253215-6abfb959-a462-4fb7-8efe-b7e720010788.png">
 <img width="40%" alt="NavigationBar" height="500" src="https://user-images.githubusercontent.com/58775369/156253228-af443158-bafa-41dd-9655-dd24ad45a790.png">
 <img width="40%" alt="NavigationBar" height="500" src="https://user-images.githubusercontent.com/58775369/156253246-650b8af1-676c-4090-bb30-542bf3c3c488.png">


We obviously used firebase realtime DB & firebase
Storage , further info will be provided down below (video footage & status-screenshots)
Brief :
In our application, with the help of Adapters we successfully retrieved data from our realtime Database and inflated it in RecyclerView , that way our recycler view in both Products activity & cart activity are real-time updated in the screen and our DB .
We applied this adapter in both Product’s activity and Cart activity .
Regarding the increase & decrease quantity buttons we simply implemented onClickListener’s that update the desired value (in our case quantity) and according to it updated the field (total desired product price) & with the help of the functionality of adapters the view’s in our parent view are Real-Time updated when doing so (increasing or decreasing quantity of  product x), in Addition to remove button which gives up a pop-up dialog to make sure the user really needs to remove it totally , if yes the child of (auth.getCurrentUser().getUid()).child(product name) is removed .
Once an order is placed we take all the children of the cart and place them as children of orders root In our DB , that way when returning to cart the previous cart won’t appear anymore since it’s already confirmed & placed 
With the help of firebaseStorage instance we can retrieve the pictures of each product easily with a unique key of each product called utc with the help of push().getKey() & getdownloadUrl(“pic.png”) , thus we won’t encounter any further conflicts of overridden products 

