const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');

const PORT = 3000;
const app = express();
app.use(bodyParser.json());
app.use(cors());
app.get("/emp", function (req, resp) {
    resp.send('[{"empId":100,"name":"Shantanu","city":"Hyderabad","salary":56000}]');
}
);
app.post('/enroll', function(req, res) {
  
    console.log(req.body)
    res.status(200).send({"message": "Data received"});
  })
app.listen(PORT,()=>console.log("Server is running.."+PORT));