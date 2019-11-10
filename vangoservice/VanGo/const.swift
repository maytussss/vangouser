//
//  const.swift
//  VanGo
//
//  Created by sanpoom punapanont on 28/10/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import Foundation
import UIKit
class Const
{
    func ShowAleart(title : String ,message : String , ViewController : UIViewController)
    {
        let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
        let resultAlert =  UIAlertAction(title: "Try Again", style: .cancel, handler: nil)
        alert.addAction(resultAlert)
        ViewController.present(alert, animated: true, completion: nil)
    }
}
