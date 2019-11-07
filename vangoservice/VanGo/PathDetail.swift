//
//  PathDetail.swift
//  VanGo
//
//  Created by sanpoom punapanont on 7/11/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import UIKit

class PathDetail: UIViewController {
    

    @IBOutlet weak var stop_name: UILabel!
    @IBOutlet weak var start_name: UILabel!
    
    override func viewDidLoad()
    {
        super.viewDidLoad()
        start_name.text = array[myIndex]
        stop_name.text = stopArray[myIndex]
        
        //stop_name.text = stopArray[myIndex]

        // Do any additional setup after loading the view.
    }
    
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destination.
        // Pass the selected object to the new view controller.
    }
    */

}
