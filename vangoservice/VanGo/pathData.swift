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


//class messageData
//{
//    private var _msgText:String!
//    var MyText:String{
//        return _msgText
//    }
//    init(msgText:String) {
//
//        self._msgText = msgText
//    }
//}

struct Path {
    var ID:String
    var start:String
    var stop:String
    var docID:String
    var firstTrip:String
    var lastTrip:String
    
    var dictationary:[String:Any]
    {
        return[
            "ID":ID,
            "start":start,
            "stop":stop,
            "docID":docID,
            "firstTrip":firstTrip,
            "lastTrip":lastTrip
        ]
    }
}

extension Path : DocumentSerializable{
    init?(dictionary dictationary:[String:Any])
    {
        guard let ID = dictationary["ID"]as? String,
              let start = dictationary["start"]as? String,
            let stop = dictationary["stop"] as? String,
            let docID = dictationary["docID"] as? String,
            let firstTrip = dictationary["firstTrip"]as? String,
            let lastTrip = dictationary["lastTrip"]as? String
            
            else
        {return nil}
        
        self.init(ID:ID,start:start,stop:stop,docID:docID,firstTrip: firstTrip,lastTrip: lastTrip)
         
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
