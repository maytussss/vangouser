//
//  userData.swift
//  VanGo
//
//  Created by sanpoom punapanont on 29/10/2562 BE.
//  Copyright © 2562 sanpoom punapanont. All rights reserved.
//

import Foundation
class userData
{
    static let start = "start"
    static let stop = "stop"

    private var _start:String! = ""
    private var _stop:String! = ""

    var Start:String
    {
        return _start
    }
    var Stop:String
    {
        return _stop
    }

    init(START :String,STOP:String)
    {
        self._start = START
        self._stop = STOP
    }
}
