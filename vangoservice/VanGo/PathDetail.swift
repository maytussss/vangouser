//
//  PathDetail.swift
//  VanGo
//
//  Created by sanpoom punapanont on 7/11/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import UIKit
import Firebase
import Foundation
import SafariServices

class PathDetail: UIViewController,UITextFieldDelegate,UITableViewDelegate,UITableViewDataSource {
    

    @IBOutlet weak var userTable: UITableView!
    @IBOutlet weak var stop_name: UILabel!
    @IBOutlet weak var start_name: UILabel!
    @IBOutlet weak var QR: UIImageView!
    @IBOutlet weak var docID: UILabel!
    @IBOutlet weak var schedule: UILabel!
    var USER = [String]()
    var tokenid = [String]()
    // บวกtokensหลัง &
    let url = "us-central1-vango-2edd2.cloudfunctions.net/pushnoti?msg=hello&"
    var tokensid = ""
    
    public var PathDataArray = [Path]()
    public var userArray = [Queue]()
    public var db:Firestore!
    public var IDcollec = ""
    public var docIDButt = ""

    

    
    
    override func viewDidLoad()
        {
            super.viewDidLoad()
            loadData()
            userTable.delegate = self
            userTable.dataSource = self
            start_name.text = array[myIndex]
            stop_name.text = stopArray[myIndex]
            docID.text = docIDarray[myIndex]
            schedule.text = "\(firstTripArray[myIndex]) - \(lastTripArray[myIndex])"
            
            let db = Firestore.firestore()
            
            // get data once time
            db.collection("trip").document(docIDarray[myIndex]).collection("queue").order(by: "timestamp").limit(to: 10).getDocuments() { (querySnapshot, err) in
                if let err = err {
                    print("Error getting documents: \(err)")
                } else {
                    for document in querySnapshot!.documents {
                        print("\(document.documentID) => \(document.data())")
                        self.USER.append(document.documentID)
    //                    print(type(of:data))
                    }
                }
                
            }
            
            db.collection("trip").document(docIDarray[myIndex]).collection("queue").order(by: "timestamp").limit(to: 10).getDocuments() { (querySnapshot, err) in
                if let err = err {
                    print("Error getting documents: \(err)")
                } else {
                    for document in querySnapshot!.documents {
                            db.collection("trip").document(docIDarray[myIndex]).collection("queue").document(document.documentID).collection("token").getDocuments(){ (querySnapshot, err) in
                                            if let err = err {
                                                print("Error getting documents: \(err)")
                                            } else {
                                                for document in querySnapshot!.documents {
                                                    let data = document.data()
                                                    let token = data["token"] as? String
                                                    self.USER.append(document.documentID)
                                                    self.tokenid.append(token!)
                                                }
                                        }
                                
                                    }
                            }
                                             
                    }

            }
            
            
            
            if let myString = docID.text
            {
                let data = myString.data(using: .ascii,allowLossyConversion: false)
                let filter = CIFilter(name: "CIQRCodeGenerator")
                filter?.setValue(data, forKey: "inputMessage")
                let ciImage = filter?.outputImage
                let transform = CGAffineTransform(scaleX: 8, y: 8)
                let transformImage = ciImage?.transformed(by: transform)
                let image = UIImage(ciImage: transformImage!)
                QR.image = image
                
            }
          
        }
    
    // delete all data in array
    @IBAction func eiei(_ sender: Any) {
        print("folkfolkfolk")
        print("USERBEFOREDELETE ",USER)
        for i in USER{
        let db = Firestore.firestore();
            db.collection("trip").document(docIDarray[myIndex]).collection("queue").document(i).delete() { err in
                        if let err = err {
                            print("Error removing document: \(err)")
                        } else {
                            print("Document successfully removed!")
                        }
            }
        }
        USER = [String]()
        print("USERAFTERDELETE ",USER)
        print(tokenid)
    }
    
    @IBAction func printidd(_ sender: Any) {
        print(tokenid)
        let joined = self.tokenid.joined(separator:"\",\"")
           
        let tokens = "tokens=[\""+joined+"\"]"
           
        self.tokensid += self.url+tokens
        print("\"https://"+self.tokensid+"\"")
        showSafari(for: "\"https://"+self.tokensid+"\"")
    }
    
    func showSafari(for url:String){
        guard let url = URL(string: url) else {
            return
        }
        let safariVC = SFSafariViewController(url: url)
        present(safariVC, animated: true)
    }
    
    override func viewWillAppear(_ animated: Bool)
    {
        
        //db =  Firestore.firestore()
        loadData()
        checkForUpdate()
        
    }
    func checkForUpdate()
    {
        self.userTable.reloadData()
        
    }
    func loadData()
    {
        let db2 = Firestore.firestore();
        db2.collection("trip").document(docIDarray[myIndex]).collection("queue").getDocuments()
            {
                querySnapshot , error in
                if error != nil
                {
                    print("errorrrrr")
                }
                else{
//                    for document in querySnapshot!.documents {
//                        print("\(document.documentID) => \(document.data())")
//                    }

                    self.userArray = querySnapshot!.documents.compactMap({Queue(dictionary: $0.data())})
                    print(self.userArray)
                    DispatchQueue.main.async {
                        self.userTable.reloadData()
                    }
                }
                
        }
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return userArray.count
        //return 6
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        
        _ = userArray[indexPath.row]
        let  cell = tableView.dequeueReusableCell(withIdentifier: "showUser", for : indexPath)
        let user = userArray[indexPath.row]

        
        if user.status == "pass"
        {
            cell.textLabel?.text = "status No. \(indexPath.row)  : \(user.status)"
            cell.backgroundColor = UIColor.green
        }
        else{
            cell.textLabel?.text = "status No. \(indexPath.row)  : wait..."
        }
        //cell.textLabel?.text = "Sanpoom "

        print("indexPath")
        return cell
        
    }
    
}

