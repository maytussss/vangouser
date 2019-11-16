//
//  Utilities.swift
//  softdevdesignregis
//
//  Created by Niboon Boonprakob on 27/10/2562 BE.
//  Copyright © 2562 Kanut. All rights reserved.
//

import Foundation
import UIKit

class Utilities{
    
    static func styleTextField(_ textfield:UITextField){
        
        //create the bottin line
        
        let bottomline = CALayer()
        
        bottomline.frame = CGRect(x: 0, y: textfield.frame.height - 2, width: textfield.frame.width, height: 2)
        
        bottomline.backgroundColor = UIColor.init(red: 48/255, green: 173/255, blue: 99/255, alpha: 1).cgColor
        
        //Remove border on textfield
        
        textfield.borderStyle = .none
        
        //Add the line to text field
        
        textfield.layer.addSublayer(bottomline)
        
    }
    
    static func styleFilledButton(_ button:UIButton){
        
        // Field rounded corner style
        button.backgroundColor = UIColor.init(red: 48/255, green: 173/255, blue: 99/255, alpha: 1)
        button.layer.cornerRadius = 25.0
        button.tintColor = UIColor.white
        
    }
    
    static func styleHollowButton(_ button:UIButton){
        
        // Hollow rounded corner style
        button.layer.borderWidth = 2
        button.layer.borderColor = UIColor.black.cgColor
        button.layer.cornerRadius = 25.0
        button.tintColor = UIColor.black
        
    }
    
    static func isPasswordValid(_ password : String) -> Bool {
        
        let passwordTest = NSPredicate(format: "SELF MATCHES %@", "^(?=.*[a-z])(?=.*[$@$#!%*?&])[A-Za-z\\d$@$#!%*?&]{8,}")
        
        return passwordTest.evaluate(with: password)
        
        
    }
    
}
