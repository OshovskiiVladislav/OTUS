import {Genre} from "./genre";
import {Author} from "./author";


export class Book {
  constructor(
    public id: number,
    public title: string,
    public authorsList: Author[],
    public genresList: Genre[]) {
  }
}
