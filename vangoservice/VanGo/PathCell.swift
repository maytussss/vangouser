//
//  PathCell.swift
//  VanGo
//
//  Created by sanpoom punapanont on 2/11/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import UIKit
import FirebaseFirestore
import Firebase

class PathCell: UITableViewCell {
    @IBOutlet weak var start: UILabel!
    @IBOutlet weak var stop: UILabel!
    @IBOutlet weak var start_label: UILabel!
    @IBOutlet weak var stop_label: UILabel!
    
    func setValue(data:Path)
    {
        start_label.text = data.start
        stop_label.text = data.stop
    }
//    func setValueStop(PathData:pathData)
//    {
//        stop.text = PathData.PathStop
//    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        // Initialization code
    }

    override func setSelected(_ selected: Bool, animated: Bool) {
        super.setSelected(selected, animated: animated)

        // Configure the view for the selected state
    }

}
