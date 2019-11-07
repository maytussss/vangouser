//
//  SetPath.swift
//  VanGo
//
//  Created by sanpoom punapanont on 28/10/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import UIKit
import Firebase
import FirebaseFirestore
import FirebaseAuth

class SetPath: UIViewController
{
    @IBOutlet weak var firstname: UITextField!
    @IBOutlet weak var surname: UITextField!
    @IBOutlet weak var IDnumber: UITextField!
    @IBOutlet weak var start: UITextField!
    @IBOutlet weak var traget: UITextField!
    @IBOutlet weak var seats: UITextField!
    @IBOutlet weak var price: UITextField!
    
    @IBAction func save(_ sender: Any)
    {
        if(IDnumber.text!.count != 13)
        {
            IDnumber.backgroundColor = UIColor(red: 255/255, green: 209/255, blue:220/255, alpha: 1)
            Const().ShowAleart(title: "LOL Bitch", message: "Are you fucking idiot? \nThat isn't your \n'FUCKING IN'", ViewController: self)
            
        }
        else
        {
            IDnumber.backgroundColor = UIColor.white
            let _db =  Firestore.firestore()
            var ref:DocumentReference? = nil
            //var docID = ref?.documentID
            ref = _db.collection("ticket").addDocument(data: ["driver ":firstname.text,"ID":IDnumber.text,"start":start.text,"stop":traget.text,"seats":"10","price":price.text])
            {
                error in
                    if let error = error
                    {
                        print("error")
                    }
                    else
                    {
                        print("add")
                    }
                
            }
            
            let alert = UIAlertController(title: "กด Done!", message: "already added", preferredStyle: .alert)
            let resultAlert =  UIAlertAction(title: "Back to Select path", style: .default) { (alertAction) in
                self.navigationController?.popViewController(animated: true)
            }
            alert.addAction(resultAlert)
            self.present(alert, animated: true, completion: nil)
            
        }
    }
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        let tap:UITapGestureRecognizer = UITapGestureRecognizer(target: self, action:#selector(SetPath.dismissKeyboard) )
        view.addGestureRecognizer(tap)
        
    }
    @objc func dismissKeyboard()
    {
        view.endEditing(true)
    }
    
}
