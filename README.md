# Android101
# Task 2-Not So Dummy Listing App

# Description
This app is a list view which contain of card view. Inside the card view have few items which is image, titles, sub titles, and also the description. When the items display in the form of card view, "..." will be shown if there is not enough space for the words to be display. 

When user select one of the items in list view, it will lead user to a new activity page which is display the item details. The data is from the Room Database. There will be a back button at the toolbar which can allow the user to return back to the list view page and the item detail activity can be scrollable. 

In the listing page, i had provided a floating action button in the bottom right area which can lead user to the add item page. User can filled in the title, sub title, description and also the url of the image. When the user lose focus from the edit Text of the url, the preview image will load and show to user. While loading, it will show a load image to prove that it is still loading and there will be a broken image to show if the url is an invalid url. After finish fill in, user can choose the "Save" button below the preview image to store the information to Room Database. The data will show immediately in the listing page. There will be a back button to allow user to go back listing page while the user wish not to add any item information.

Moreover, user had select one of the item in the listing page to show the detail. In the detail page, I had provide a floating action button in the bottom right area which can lead user to update item detail page. User can update the sub title, description and also the url. When the user lose focus on the edit Text of the url, the preview image will load and show to the user. While loading, it will show a load image to prove that it is still loading and there will be a broken image to show if the url is an invalid url. After finish update, user can choose the update button below the preview image to update the data to Room Database. User can select the back button if the user not wish to update the information and it will return the user to listing item page.
