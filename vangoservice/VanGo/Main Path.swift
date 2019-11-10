//
//  Main Path.swift
//  VanGo
//
//  Created by sanpoom punapanont on 2/11/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import UIKit
import FirebaseFirestore
import Firebase
public var myIndex = 0
var array = [String]()
var stopArray = [String]()
var docIDarray = [String]()
var firstTripArray = [String]()
var lastTripArray = [String]()

class Main_Path: UIViewController,UITextFieldDelegate,UITableViewDelegate,UITableViewDataSource
{
    public var PathDataArray = [Path]()
    //var DataArray = [messageData]
    public var db:Firestore!
    public var IDcollec = ""
    public var docIDButt = ""



    @IBOutlet weak var pathTable: UITableView!
    //@IBOutlet weak var start_station: UILabel!
    //@IBOutlet weak var stop_station: UILabel!
    
    @IBAction func Go(_ sender: Any)
    {
        print(docIDButt)
    }

    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        pathTable.delegate = self
        pathTable.dataSource = self
        
        db =  Firestore.firestore()
        loadData()
        checkForUpdate()
        array = []
        stopArray = []
        docIDarray = []
        
    }
    
    override func viewWillAppear(_ animated: Bool)
    {
        
        //db =  Firestore.firestore()
        loadData()
        checkForUpdate()
        array = []
        stopArray = []
        docIDarray = []
        
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath)
    {
        myIndex = indexPath.row
        performSegue(withIdentifier: "segue", sender: self)
    }
    
    
    func loadData()
    {
        db.collection("trip").getDocuments()
            {
                querySnapshot , error in
                if let error = error
                {
                    print("errorrrrr")
                }
                else{

                    self.PathDataArray = querySnapshot!.documents.flatMap({Path(dictionary: $0.data())})
                    
                    DispatchQueue.main.async {
                        self.pathTable.reloadData()
                    }
                }
                
        }
    }
    
    func checkForUpdate()
    {
        self.pathTable.reloadData()
        
    }
    
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return PathDataArray.count
    }
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell
    {
        
        _ = PathDataArray[indexPath.row]
        let  cell = tableView.dequeueReusableCell(withIdentifier: "showPath", for : indexPath)
        let path = PathDataArray[indexPath.row]
        array.append(path.start)
        stopArray.append(path.stop)
        docIDarray.append(path.docID)
        firstTripArray.append(path.firstTrip)
        lastTripArray.append(path.lastTrip)
        
        cell.textLabel?.text = "From \t : \(path.start) : \t To \t : \(path.stop)"
        //cell.textLabel?.textColor = UIColor.blue
        //start_station.text = path.start
        //stop_station.text = path.stop
        docIDButt = path.docID
        print(indexPath)
        return cell
        
    }

}
