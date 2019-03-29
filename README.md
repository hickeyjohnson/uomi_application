# uomi

![uomiAppIcon](https://github.com/hickeyjohnson/uomi_application/blob/master/app/src/main/ic_glossy_app_icon-web.png)

"uomi" is an android application that tracks financial transactions between friends or groups of friends. The name is derived from the phrase "you owe me \[money\]". Created by Nathaniel Johnson &amp; Matthew Hickey for a Computer Engineering design project. 

## Getting Started

This application was developed and tested using Android Studio, and it is recommended that it be used for continuing development.

To download this project, simply clone the repository to your local machine.

Open Android Studio and open the UomiApplication project.

### Prerequisites

As stated previously, Android Studio is critical for developing this application.

Also be sure to install the Android API of at least version 25 (7.1.1 Nougat), as this was the minimum required version.
It is also recommended that when emulating the application that you use an emulator running API 25 (7.1.1 Nougat),
as much of the design was done with this software release. This is done easily in Android Studio when
setting up a new virtual device (pressing the run/play button and creating a new device).

## Emulating/Running the application

Emulation of uomi can be achieved within Android Studio. Once the project opens and finishes indexing,
simply press the Run/Play button to build and run the app. In the menu that opens, select **Create New Device**
, selecting any device you wish (recommended Pixel 2). Click **Next** and ensure (as stated previously)
that the software version selected to run on the emulator is at least Nougat 7.1.1 (API 25).

Once the emulator is set up, following a successful build the app will eventually open in the emulator.
Build and run status can be viewed in the bottom pane of Android Studio's default view.

**Note**: Feel free to run this on your own Android device in debugger mode. However, note that the login
will likely take your own Facebook account for authentication automatically. If you do not want this, please 
use a new emulator, or log out of Facebook prior to the next step.

### Logging into Uomi
The recommended way to log into uomi is through the Facebook API. Do not log in through the standard
email/password fields as this feature is deprecated. The following test login information is given for logging
into the app with Facebook:

* **Email:** tom_onoohnl_riddle@tfbnw.net
* **Password:** nimbus2000

This should bring you to the uomi dashboard following authentication with Facebook. If for some reason
it doesn't work immediately, click the log out button and try Facebook authentication again. It should
work the second time.

### Navigation

Navigate throughout uomi using the bottom navigation bar. The four main views are (from left to right)
Dashboard, Accounts, Notifications (not implemented), and Settings (only logout implemented).

### The Dashboard

A successful login brings the user to the dashboard, where a welcome message and their net balance
across all their accounts is displayed.

### Accounts

This view contains a list of the accounts of which the current user is a part. The sum of all accounts
is the user's net balance which is displayed on the dashboard. The blue floating action button in the
bottom-right corner of the screen is used to create a new account. For testing purposes, create either
a group or non-group account with one/multiple of the following emails:

* percy_xuvelkm_jackson@tfbnw.net
* katehickey8@hotmail.com

When confirming this creation, the new account will appear on the top of the accounts view.

### Account Transactions

uomi's essential purpose is to track transactions between users or multiple users. Within an account,
past transactions can be viewed and deleted, and new ones can be added to affect the user's balance
for that account. To view the contents of an account, tap any account on the accounts page.

To delete a transaction, swipe it from right to left and confirm the delete action. To add a transaction
(you are owed money), tap the floating action button in the bottom right corner and give the details
of the transaction item. This new item will appear as you owed money on your phone, and other members
of the account owing money on their phones.

### Logging out

To log out of uomi, proceed to the settings view using the bottom navigation bar. Tap the Logout option
and you will be returned to the login screen. 


## Web API

For the Swagger documentation for endpoints that the UOMI API employs, please see the link below:
* [API Documentation](http://uomi-api.herokuapp.com/uomi_api/ui/#/)

For the source code for the API (not needed for demonstrating the app, automatically uses live API)

* [API Source Code](https://github.com/hickeyjohnson/uomi_api)

## Built With

* [Android Studio](https://developer.android.com/studio/) - Android development IDE

## Authors

* **Matthew Hickey**

* **Nathaniel Johnson**

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Dr. Vardy of Memorial University of Newfoundland
* GitHub User fabiomsr for the [MoneyTextView](https://github.com/fabiomsr/MoneyTextView)
