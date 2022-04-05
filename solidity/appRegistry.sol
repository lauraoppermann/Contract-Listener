// SPDX-License-Identifier: GPL-3.0
pragma solidity ^0.8.11;

// ropsten 0xBe6A024148C63C2abBE5685CbeF3603089Ab8727

contract AppRegistry {
    ////////////////////////////////////////////////////////////////
    // APPS / APIS
    ////////////////////////////////////////////////////////////////

    // string appOwner;

    mapping(string => address) private appNames; // name => appID

    // appTypes stores the type of an app
    mapping(address => string) private appTypes; // appID => APP/API

    event AppRegisteredEvent(
        address indexed appId,
        string name,
        string appType
    );

    //event AppNameRegisteredEvent(address indexed appId, string name);

    /**
     * Creates a new app with a given appID and name and url
     *
     * returns true if successful
     */
    function createApp(
        address appId,
        string calldata name,
        string calldata appType
    ) public returns (bool) {
        appTypes[appId] = appType;
        appNames[name] = appId;

        emit AppRegisteredEvent(appId, name, appType);
        //emit AppNameRegisteredEvent(appId, name);

        return true;
    }

    /**
     * returns address of app by given name. returns 0 if name is not found
     */
    function getAppID(string calldata name) public view returns (address) {
        return appNames[name];
    }
}
