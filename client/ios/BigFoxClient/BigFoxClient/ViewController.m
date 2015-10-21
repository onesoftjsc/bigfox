//
//  ViewController.m
//  BigFoxClient
//
//  Created by QuyenNX on 9/4/15.
//  Copyright (c) 2015 QuyenNX. All rights reserved.
//

#import "ViewController.h"
#import "ConnectionManager.h"
#import "CSName.h"
#import "CSChat.h"

static ViewController* _instance = nil;

@implementation ViewController

@synthesize loginView, chatView;
@synthesize btnLogin, btnSend;
@synthesize textMessage, textName;
@synthesize listMessage;
@synthesize messages;


- (void)viewDidLoad {
    [super viewDidLoad];
    [[ConnectionManager getInstance] startThread];
    
    messages = [[NSMutableArray alloc] init];
    
    self.listMessage.delegate = self;
    self.listMessage.dataSource = self;
    _instance = self;
//    self.listMessage.delegate = self;
//    self.listMessage.dataSource = self;
    // Do any additional setup after loading the view, typically from a nib.
}

+ (ViewController*) getInstance {
    if (_instance == nil) {
        _instance = [[ViewController alloc] init];
    }
    return _instance;
}

- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

- (IBAction)sendClicked:(id)sender {
    NSString* strChat = [NSString stringWithString:textMessage.text];
    if ([strChat length] > 0) {
        [[ConnectionManager getInstance] write:[[CSChat alloc] initWithMessage:strChat]];
        [textMessage setText:@""];
    }
    
}

- (IBAction)LoginClicked:(id)sender {
    NSString* strName = [NSString stringWithString:textName.text];
    [[ConnectionManager getInstance] write:[[CSName alloc] initWithMessage:strName]];
    [self.loginView setHidden:TRUE];
}

-(void) addMessage:(NSString *)message {
    [self.messages addObject:message];
    [self.listMessage reloadData];
    NSIndexPath *topIndexPath = [NSIndexPath indexPathForRow:self.messages.count-1
                                                   inSection:0];
    [self.listMessage scrollToRowAtIndexPath:topIndexPath
                      atScrollPosition:UITableViewScrollPositionMiddle
                              animated:YES];
}

- (void) showAlert:(NSString*)title :(NSString *)message {
    UIAlertView* alert = [[UIAlertView alloc] initWithTitle:title message:message delegate:nil cancelButtonTitle:@"OK" otherButtonTitles:nil, nil];
    [alert show];
}

#pragma mark -
#pragma mark Table delegates

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    
    NSString *s = (NSString *) [messages objectAtIndex:indexPath.row];
    
    static NSString *CellIdentifier = @"ChatCellIdentifier";
    
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:CellIdentifier];
    if (cell == nil) {
        cell = [[UITableViewCell alloc] initWithStyle:UITableViewCellStyleDefault reuseIdentifier:CellIdentifier];
    }
    
    cell.textLabel.text = s;
    
    return cell;
    
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return messages.count;
}

- (void)viewDidUnload {
    // Release any retained subviews of the main view.
    // e.g. self.myOutlet = nil;
}

@end
