import * as functions from "firebase-functions";
import * as admin from "firebase-admin";
admin.initializeApp(functions.config().firebase);

// // Start writing Firebase Functions
// // https://firebase.google.com/docs/functions/typescript
//
/* export const helloWorld = functions.https.onRequest((request, response) => {
  console.log("First cloud function trigerred and logged.");
  return admin
    .database()
    .ref("/Food")
    .once("value", snapshot => {
      console.log("Inside once method.");
      console.log(snapshot.val());
      response.send("data is: " + snapshot.val());
    })
    .catch(err => {
      console.log("Inside error method.");
      response.status(500).send(err);
    });
  //   response.send("Hello from Firebase for the first time!");
}); */

/* export const onDatabaseTestEntry = functions.database
  .ref("Testing Purposes/testData2")
  .onUpdate(change => {
    console.log(change);
    console.log(change.before.child("category").val());
    return change.after.ref.child("1/category").set("changed");
    // change.after.child("category").val() = "Changed";
    // return null;
  }); */

export const onDatabaseOrderTestEntry = functions.database
  .ref("UserOrderData/{rollNo}/{newOrderId}")
  .onCreate(async (snapshot, context) => {
    const value = snapshot.val();
    // const parentSnap = snapshot.ref.parent;
    console.log(value);
    console.log(context.params.newOrderId);
    console.log(context.params.rollNo);
    for (const cartItem of snapshot.child("cartItems").val()) {
      console.log(cartItem.cartItemName);
    }
    /* snapshot.child("cartItems").forEach(child => {
      child.child("itemStatus").ref.set("Received");
    }); */
    return snapshot
      .child("status")
      .ref.set("In-Progress")
      .then(async event => {
        await admin
          .database()
          .ref("UserOrderData")
          .child(context.params.rollNo)
          .once("value")
          .then(async snap => {
            await snap
              .child(context.params.newOrderId)
              .child("displayID")
              .ref.set(snap.numChildren().toString());
          });
        // const actualSnap = await parentSnap.once("value");

        // actualSnap.child("cartItems").forEach(snap => {
        // console.log(snap);
        // });
      });
  });

/* export const onNewUserRegister = functions.auth.user().onCreate(user => {
  const emailID: string = user.email as string;
  const isVerified = user.emailVerified;
  console.log(emailID + " testing " + isVerified);
  //   add entry to data

  const rollNo = emailID.split("@", 1)[0];
  console.log(rollNo);
  admin
    .database()
    .ref("OrderData/" + rollNo)
    .child("Data")
    .set("Registered")
    .then(res => {
      console.log("Entry registered for new user: " + res);
      return null;
    })
    .catch(err => {
      console.log("Error occurred: " + err);
      return null;
    });

  return null;
}); */

/* export const byeWorld = functions.https.onRequest((request, response) => {
  console.log("Bye world cloud function trigerred.");
  response.send("Bye from Firebase!!!");
}); */