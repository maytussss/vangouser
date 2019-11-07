//
//  pathData.swift
//  VanGo
//
//  Created by sanpoom punapanont on 2/11/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import Foundation
import FirebaseFirestore

protocol DocumentSerializable {
    init?(dictionary:[String:Any])
}

struct Path {
    var ID:String
    var start:String
    var stop:String
    
    var dictationary:[String:Any]
    {
        return[
            "ID":ID,
            "start":start,
            "stop":stop
        ]
    }
}

extension Path : DocumentSerializable{
    init?(dictionary dictationary:[String:Any])
    {
        guard let ID = dictationary["ID"]as? String,
              let start = dictationary["start"]as? String,
            let stop = dictationary["stop"] as? String
            else
        {return nil}
        
        self.init(ID:ID,start:start,stop:stop)
         
    }
}







//class pathData
//{
//    static let START_ID = "Start"
//    private var _pathStart:String!
//    private var _pathStop:String!
//    var PathStart:String
//    {
//        return _pathStart
//    }
////    var PathStop:String
////    {
////        return _pathStop
////    }
//
//    init (pathStart:String)
//    {
//        self._pathStart = pathStart
//    }
////    init (pathStop:String)
////    {
////        self._pathStop = pathStop
////    }
//}