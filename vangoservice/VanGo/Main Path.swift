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

class Main_Path: UIViewController,UITextFieldDelegate,UITableViewDelegate,UITableViewDataSource
{
    public var PathDataArray = [Path]()
    public var db:Firestore!
    public var IDcollec = ""



    @IBOutlet weak var pathTable: UITableView!
    
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        pathTable.delegate = self
        pathTable.dataSource = self
        
        db =  Firestore.firestore()
        loadData()
        checkForUpdate()
        
    }
    
    override func viewWillAppear(_ animated: Bool) {
        
        //db =  Firestore.firestore()
        loadData()
        checkForUpdate()
    }
    
    func loadData()
    {
        db.collection("ticket").getDocuments()
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
        cell.textLabel?.text = "\(path.start)   =>  \(path.stop)"
        print(indexPath)
        return cell
        
    }

}
