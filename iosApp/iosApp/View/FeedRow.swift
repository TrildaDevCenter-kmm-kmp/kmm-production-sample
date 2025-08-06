//
//  FeedRow.swift
//  iosApp
//
//  Created by Ekaterina.Petrova on 11.11.2020.
//  Copyright © 2020 orgName. All rights reserved.
//

import SwiftUI
import RssReader
import URLImage

struct FeedRow: View {
    let feed: RssFeed
    
    private enum Constants {
        static let imageWidth: CGFloat = 20.0
    }
    
    var body: some View {
        HStack {
            if let channel = feed.channel {
                if let imageUrl = channel.image?.url, let url = URL(string: imageUrl) {
                    URLImage(url: url) { image in
                        image
                            .resizable()
                            .aspectRatio(contentMode: .fill)
        
                    }
                    .frame(width: Constants.imageWidth, height: Constants.imageWidth)
                    .clipped()
                    .cornerRadius(Constants.imageWidth / 2.0)
                }
                VStack(alignment: .leading, spacing: 5.0) {
                    if let title = channel.title {
                        Text(title).bold().font(.title3).lineLimit(1)
                    }
                    if let descr = channel.description_ {
                        Text(descr).font(.body)
                    }
                }
            }
            
        }
    }
}



