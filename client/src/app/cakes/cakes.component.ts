import { Component, OnInit } from '@angular/core';
import { Cake } from '../cake';
import { CakeService } from '../cake.service';
import { AppModuleService } from '../app-module.service';


@Component({
  selector: 'app-cakes',
  templateUrl: './cakes.component.html',
  styleUrls: ['./cakes.component.css']
})
export class CakesComponent implements OnInit {
  cake = new Cake();
  cakes : Cake[];
  savedCakes : Cake[];
  //visible = false;

  constructor(private cakeService: CakeService, private appModuleService: AppModuleService) { }

  ngOnInit(): void {
  //this.appModuleService.setVisible(false);
  //console.log('cakes component isVisible: '+ this.appModuleService.isVisible());
  this.getAllCakes();
    this.downloadCakes();
  }

  getAllCakes(){
    this.cakeService.getSavedCakes()
        .subscribe(savedCakes => this.savedCakes = savedCakes);
  }

 downloadCakes():void{
    this.cakeService.getCakes()
    .subscribe(cakes => this.cakes = cakes);
  }

    add(title: string, desc: string, image: string): void{
      title = title.trim();
      desc = desc.trim();
      image = image.trim();

      if (!title && !desc && !image){
        return;
      }
      this.cakeService.addCake({title, desc, image} as Cake)
        .subscribe(cake => {
            this.cakes.push(cake);
        });
    }

}
