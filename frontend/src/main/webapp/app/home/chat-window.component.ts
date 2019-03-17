import { Component, AfterViewInit } from '@angular/core';

@Component({
    selector: 'jhi-chat-window',
    templateUrl: './chat-window.component.html',
    styleUrls: ['./chatwindow.scss']
})
export class ChatWindowComponent implements AfterViewInit {
    xhr: XMLHttpRequest;
    iframeSrc: any;

    constructor() {}

    ngAfterViewInit() {
        this.xhr = new XMLHttpRequest();
        this.xhr.open('GET', 'https://webchat.botframework.com/api/tokens', true);
        this.xhr.setRequestHeader('Authorization', 'BotConnector ' + 'FXmTs67RP_k._cd8L_WjMzOkY0FyR6D4xCPJSdT6UvptpwRf722ZLvQ');
        this.xhr.send();
        this.xhr.onreadystatechange = this.processRequest.bind(this);
    }

    processRequest(e) {
        if (e.currentTarget && e.currentTarget.readyState === 4 && e.currentTarget.status === 200) {
            const response = JSON.parse(e.currentTarget.responseText);
            this.iframeSrc = 'https://webchat.botframework.com/embed/mindshopper-bot?t=' + response;
        }
    }
}
