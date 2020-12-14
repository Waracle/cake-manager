import { Component, OnInit } from '@angular/core';
import { Cake } from '../cake';
import { CakeService } from '../cake.service';
import { AppModuleService } from '../app-module.service';

@Component({
  selector: 'app-display-cakes',
  templateUrl: './display-cakes.component.html',
  styleUrls: ['./display-cakes.component.css']
})
export class DisplayCakesComponent implements OnInit {
    cake = new Cake();
    cakes : Cake[];
    visible: boolean;

  constructor(private cakeService: CakeService, private appModuleService: AppModuleService) { }

    ngOnInit(): void {
      this.getCakes();
    }

     getCakes():void{
        this.cakeService.getSavedCakes()
        .subscribe(cakes => this.cakes = cakes);
      }
}
