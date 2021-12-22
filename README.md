# Sportify
### Sportify app is a store to buy sport equipments
### Category
![image](https://github.com/Abodi-Massarweh/Sportify/blob/cdfea674b26035cf0e4345a06c2fc42d63c25841/Categories.jpeg)
### Explore
![image](https://github.com/Abodi-Massarweh/Sportify/blob/cdfea674b26035cf0e4345a06c2fc42d63c25841/Explore.jpeg)

### Firebase Database
#### Logging in
![image](https://github.com/Abodi-Massarweh/Sportify/blob/0310380634148eb359ecbbde7d21fd2eec81f845/Logging%20In.jpeg)

We obviously used firebase realtime DB & firebase Storage , further info will be provided down below (video footage & status-screenshots)
Brief :
In our application, with the help of Adapters we successfully retrieved data from our realtime Database and inflated it in RecyclerView , that way our recycler view in both Products activity & cart activity are real-time updated in the screen and our DB .
We applied this adapter in both Product’s activity and Cart activity .
Regarding the increase & decrease quantity buttons we simply implemented onClickListener’s that update the desired value (in our case quantity) and according to it updated the field (total desired product price) & with the help of the functionality of adapters the view’s in our parent view are Real-Time updated when doing so (increasing or decreasing quantity of  product x), in Addition to remove button which gives up a pop-up dialog to make sure the user really needs to remove it totally , if yes the child of (auth.getCurrentUser().getUid()).child(product name) is removed .
Once an order is placed we take all the children of the cart and place them as children of orders root In our DB , that way when returning to cart the previous cart won’t appear anymore since it’s already confirmed & placed 
With the help of firebaseStorage instance we can retrieve the pictures of each product easily with a unique key of each product called utc with the help of push().getKey() & getdownloadUrl(“pic.png”) , thus we won’t encounter any further conflicts of overridden products 

