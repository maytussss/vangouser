//
//  SignUpViewController.swift
//  vangosingup
//
//  Created by Niboon Boonprakob on 27/10/2562 BE.
//  Copyright © 2562 Kanut. All rights reserved.
//

import UIKit
import FirebaseAuth
import Firebase

class SignUpViewController: UIViewController {
    
    
    @IBOutlet weak var FirstnameTextField: UITextField!
    
    @IBOutlet weak var LastNameTextField: UITextField!
    
    @IBOutlet weak var EmailTextField: UITextField!
    
    @IBOutlet weak var PasswordTextField: UITextField!
    
    @IBOutlet weak var AgeTextField: UITextField!
    
    @IBOutlet weak var SexTextField: UITextField!
    
    @IBOutlet weak var SignUpButton: UIButton!
    
    @IBOutlet weak var ErrorLable: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        setUpElements()
    }
    func setUpElements(){
        
        ErrorLable.alpha = 0
        
        Utilities.styleTextField(FirstnameTextField)
        
        Utilities.styleTextField(LastNameTextField)
        
        Utilities.styleTextField(EmailTextField)
        
        Utilities.styleTextField(PasswordTextField)
        
        Utilities.styleTextField(AgeTextField)
        
        Utilities.styleTextField(SexTextField)
        
        Utilities.styleFilledButton(SignUpButton)
        
    }

    func validateField() -> String?{
        
        if FirstnameTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" ||
            LastNameTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" ||
            EmailTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" ||
            PasswordTextField.text?.trimmingCharacters(in: .whitespacesAndNewlines) == "" {
            
            return "Please fill in all field"
        }
        
        // check password
        let cleanedPassword = PasswordTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
        
        if Utilities.isPasswordValid(cleanedPassword) == false{
            
            return "Check your Password again"
        }
        return nil
    }

    func showError(_ message:String){
        ErrorLable.text = message
        ErrorLable.alpha = 1
    }
    
//    func transitionToHome(){
//
//        let homeViewController = storyboard?.instantiateViewController(identifier: Constants.Storyboard.homeViewController) as? HomeViewController
//        view.window?.rootViewController = homeViewController
//        view.window?.makeKeyAndVisible()
//    }
    
    @IBAction func SignUpTapped(_ sender: Any) {
        // Validate the field
        let error = validateField()
        
        
        
        if error != nil{
            
            showError(error!)
        }
        
        else{
            
            let firstname = FirstnameTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
            
            let lastname = LastNameTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
            
            let email = EmailTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
            
            let password  = PasswordTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
            
            let age = AgeTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
            
            let sex = SexTextField.text!.trimmingCharacters(in: .whitespacesAndNewlines)
            
            // Create the user
            Auth.auth().createUser(withEmail: email, password: password) { (result, err) in
                if err != nil{
                
                    self.showError("Error create user")
                
                }
                else{
                    
                    let db = Firestore.firestore()
                    
                    db.collection("aboutuser").addDocument(data: ["firstname":firstname,"lastname":lastname,"age":age,"sex":sex,"uid":result!.user.uid]){(error) in
                            if error != nil {
                                self.showError("WOWOWOWOW")
                            }
                        }
                    
//                    self.transitionToHome()
                }
            }
        }
}
}
