# FreshworksProject
       > DataStore is a file-based key-value data store that supports basic CRD operations like Create, Read and Delete.
       > This project requires two external jar files, It has been included in the jar folder.
       > DataStore revolves around a main menu containing different options for different operations like Creating a new entry, Creating a new entry with Time-To-Live property, Reading an entry, Deleting an entry and Exiting from the application.
       > When Creating a new entry for the first time, automatically a new file will be created and from the second time it uses the existing file to make changes.
       > Length of the given key must be at max of 32 and duplicate keys are not allowed by the application.
       > When Creating a new entry with Time-To-Live property, The entry only lives the amount of time specified by the user and when surpassing the time, the key automatically expires.
       > Read opertaion works when user enters an existing key, the JSON object is returned as output.
       > Delete operation also works when user enters an existing key, also when the last entry of file is deleted, the file is automatically deleted to prevent having an empty file.
