//
//  ViewController.h
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import <UIKit/UIKit.h>

@interface ViewController : UIViewController <UITableViewDelegate, UITableViewDataSource> {
//    UIView* loginView;
//    UIView* chatView;
//    UIButton* btnLogin;
//    UIButton* btnSend;
//    UITextField* textName;
//    UITextField* textMessage;
//    UITableView* listMessage;
    NSMutableArray* messages;
}

@property (weak, nonatomic) IBOutlet UIView *loginView;
@property (weak, nonatomic) IBOutlet UIButton *btnLogin;
@property (weak, nonatomic) IBOutlet UITextField *textName;
@property (weak, nonatomic) IBOutlet UIView *chatView;
@property (weak, nonatomic) IBOutlet UITextField *textMessage;
@property (weak, nonatomic) IBOutlet UIButton *btnSend;
@property (weak, nonatomic) IBOutlet UITableView *listMessage;

@property (nonatomic, retain) NSMutableArray *messages;
+ (ViewController *)getInstance;
- (IBAction)sendClicked:(id)sender;
- (IBAction)LoginClicked:(id)sender;
- (void) addMessage : (NSString* ) message;
- (void) showAlert : (NSString*) title : (NSString*) message ;
@end

